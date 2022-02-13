package com.board.board.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.board.board.domain.Board;
import com.board.board.domain.Heart;
import com.board.board.domain.Reply;
import com.board.board.dto.Board.BoardPostDto;
import com.board.board.dto.Heart.HeartDto;
import com.board.board.dto.oauth.SessionUser;
import com.board.board.dto.reply.ReplyDto;
import com.board.board.mapper.Board.BoardPostMapper;
import com.board.board.mapper.Heart.HeartMapper;
import com.board.board.mapper.Reply.ReplyMapper;
import com.board.board.repository.BoardRepository;
import com.board.board.service.BoardApiService;
import com.board.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@RestController
@RequestMapping("/api")
class BoardApiController {
    private final HttpSession httpSession;

    private final BoardRepository boardRepository;

    private final BoardApiService boardApiService;

    private final BoardService boardService;

    private final BoardPostMapper boardPostMapper;

    private final ReplyMapper replyMapper;

    private final HeartMapper heartMapper;

    @Autowired
    public BoardApiController(HttpSession httpSession, BoardRepository boardRepository, BoardApiService boardApiService, BoardService boardService, BoardPostMapper boardPostMapper, ReplyMapper replyMapper, HeartMapper heartMapper) {
        this.httpSession = httpSession;
        this.boardRepository = boardRepository;
        this.boardApiService = boardApiService;
        this.boardService = boardService;
        this.boardPostMapper = boardPostMapper;
        this.replyMapper = replyMapper;
        this.heartMapper = heartMapper;
    }

    @GetMapping("/boards")
    List<BoardPostDto> all(@RequestParam(required = false, defaultValue = "") String title, @RequestParam(required = false, defaultValue = "") String content ) {
        return boardApiService.findBoardApi(title, content);
    }

    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard) {
        return boardRepository.save(newBoard);
    }


    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {

        return boardRepository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {
        return boardRepository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return boardRepository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return boardRepository.save(newBoard);
                });
    }

    @PostMapping("/boards/form")
    Model form(Model model, @Valid BoardPostDto boardPostDto, BindingResult bindingResult, HttpSession httpSession){

        if(bindingResult.hasErrors()){      // 제목이 2글자 이하이거나 30자 이상인 경우 에러를 출력한다.
            System.out.println(bindingResult);
            model.addAttribute("result", "fail");
            model.addAttribute("change", "board/form :: info-form");
            return model;
        }

        System.out.println(boardPostDto.getContent());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        String userEmail = user.getEmail();

        Board board = boardPostMapper.toEntity(boardPostDto);           // mapstruct를 사용하여 Dto의 정보를 entity로 바꾸어준다.
        boardPostMapper.updateFromDto(boardPostDto, board);             // null인 값들을 빼주기 위한 updateFromDto

        boardService.save(userEmail, board);  // 글 저장 save

        model.addAttribute("result", "success");
//        return "redirect:/board/list";
        return model;
    }

//    @PreAuthorize("(isAuthenticated() and ( #userid == authentication.principal.userid )  ) or hasRole('ROLE_ADMIN')")
//    @PostAuthorize("returnObject.title == authentication.principal.username")
//    @Secured("ROLE_ADMIN") // admin사용자만 delete 메소드를 호출할 수 있도록 한다.
    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
    }


    // 좋아요/해제
    @PostMapping("/doHeart/{id}")
    public void doHeart(@PathVariable Long id){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        long userId = user.getId();

        // 현재 로그인한 아이디와, board의 Id를 바탕으로 좋아요가 되어있나 확인한다.
        Long wholeHeart = boardService.getHeartCount(id);
        HeartDto heartDto = heartMapper.toDto(boardService.getMyHeart(id, userId));
        boolean myHeart = heartDto != null;     // heartDto가 있으면 true, 없으면 false

        // 좋아요가 되어 있다면 취소해줄 예정이다.
        if(myHeart){
            boardService.deleteHeart(heartDto.getId());
        }else{
            heartDto = new HeartDto();
            heartDto.setBoardId(id);
            heartDto.setUserId(userId);
            System.out.println(heartDto);
            Heart heart = heartMapper.toEntity(heartDto);
            System.out.println(heart);
            heartMapper.updateFromDto(heartDto, heart);             // null인 값들을 빼주기 위한 updateFromDto 적용
            boardService.saveHeart(heart);
        }
    }

    //     댓글 쓰기
    @PostMapping("/doReply/{boardId}")
    public String doReply(@Valid ReplyDto replyDto, BindingResult bindingResult, HttpSession httpSession) throws Exception{

        if(bindingResult.hasErrors()) {      // 제목이 2글자 이하이거나 30자 이상인 경우 에러를 출력한다.
            StringBuilder errorMsg = new StringBuilder();

            List<ObjectError> list =  bindingResult.getAllErrors();
            for(ObjectError e : list) {
                errorMsg.append(e.getDefaultMessage());
            }
            return errorMsg.toString();
        }

        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        long userId = user.getId();

        Reply reply = replyMapper.toEntity(replyDto);
        replyMapper.updateFromDto(replyDto, reply);

        if(replyDto.getId() > 0){       // 댓글 수정시
            if(!boardService.confirmReply(replyDto.getId(), userId)){   // 본인확인 logic
                return "Nope.";
            }
        }

        boardService.saveReply(userId, reply);  // 댓글 저장 save
        return "success";
    }

    //     댓글 보여주기
    @PostMapping("/reply/{id}")
    public ModelAndView reply(ModelMap model, @PathVariable Long id) throws Exception{

        ModelAndView modelAndView = new ModelAndView();
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        modelAndView.setView(jsonView);

        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        long userId = user.getId();

        Long wholeHeart = boardService.getHeartCount(id);
        HeartDto heartDto = heartMapper.toDto(boardService.getMyHeart(id, userId));

        boolean myHeart = heartDto != null;     // heartDto가 있으면 true, 없으면 false

        model.addAttribute("heartCount", wholeHeart);
        model.addAttribute("heartUser", myHeart);
        return modelAndView;
    }


}