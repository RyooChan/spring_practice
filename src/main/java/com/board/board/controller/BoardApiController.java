package com.board.board.controller;

import java.util.ArrayList;
import java.util.List;

import com.board.board.domain.Board;
import com.board.board.dto.Board.BoardPostDto;
import com.board.board.dto.oauth.SessionUser;
import com.board.board.mapper.Board.BoardPostMapper;
import com.board.board.mapper.Reply.ReplyMapper;
import com.board.board.repository.BoardRepository;
import com.board.board.service.BoardApiService;
import com.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
class BoardApiController {

    private final BoardRepository boardRepository;

    private final BoardApiService boardApiService;

    private final BoardService boardService;

    private final BoardPostMapper boardPostMapper;

    private final ReplyMapper replyMapper;

    @Autowired
    public BoardApiController(BoardRepository boardRepository, BoardApiService boardApiService, BoardService boardService, BoardPostMapper boardPostMapper, ReplyMapper replyMapper) {
        this.boardRepository = boardRepository;
        this.boardApiService = boardApiService;
        this.boardService = boardService;
        this.boardPostMapper = boardPostMapper;
        this.replyMapper = replyMapper;
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
        boardRepository.deleteById(id);
    }

}