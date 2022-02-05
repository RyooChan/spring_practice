package com.board.board.controller;

import com.board.board.domain.Board;
import com.board.board.domain.Reply;
import com.board.board.dto.Board.BoardPostDto;
import com.board.board.dto.like.LikeDto;
import com.board.board.dto.oauth.SessionUser;
import com.board.board.dto.reply.ReplyDto;
import com.board.board.mapper.Board.BoardPostMapper;
import com.board.board.mapper.Like.LikeMapper;
import com.board.board.mapper.Reply.ReplyMapper;
import com.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final HttpSession httpSession;

    private final BoardService boardService;

    private final BoardPostMapper boardPostMapper;

    private final ReplyMapper replyMapper;

    private final LikeMapper likeMapper;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 10) Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchText){

        // List형태로 받아온 전체 board데이터를 DTO로 변환
        List<BoardPostDto> boardPostDtos = boardPostMapper.toDtos(boardService.list(searchText));
        // DTO로 변환된 boardPostDots를 다시 Page형태로 바꾸어 준다.
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), boardPostDtos.size());
        final Page<BoardPostDto> boards = new PageImpl<>(boardPostDtos.subList(start, end), pageable, boardPostDtos.size());

        // 구해진 page형태의 boards를 사용하여 시작 ~ 끝 페이지를 구할 수 있도록 한다.
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    // 글 작성페이지 이동(수정 / 작성)
    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id==null){
            model.addAttribute("boardPostDto", new BoardPostDto());
        }else{
            Board board = boardService.postForm(id);
            BoardPostDto boardPostDto = boardPostMapper.toDto(board);
            boardPostMapper.updateFromDto(boardPostDto, board);             // null인 값들을 빼주기 위한 updateFromDto 적용
            model.addAttribute("boardPostDto", boardPostDto);
        }
        return "board/form";
    }

    // 게시판의 글을 저장하고, 에러가 있으면 이를 알려준다.
    // 세션으로 저장된 userid를 가져오기 위해 HttpSession을 받아온다. 이후 그곳에 저장된 userEmail을 통해 저장한다.
    // 겹치지 않는 값은 userid를 통해 유저를 검색하고, 그 유저의 id를 저장시킨다.
    @PostMapping("/form")
    public String form(@Valid BoardPostDto boardPostDto, BindingResult bindingResult, HttpSession httpSession) throws Exception{

        if(bindingResult.hasErrors()){      // 제목이 2글자 이하이거나 30자 이상인 경우 에러를 출력한다.
            return "board/form :: info-form";
        }

        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        String userEmail = user.getEmail();

        Board board = boardPostMapper.toEntity(boardPostDto);           // mapstruct를 사용하여 Dto의 정보를 entity로 바꾸어준다.
        boardPostMapper.updateFromDto(boardPostDto, board);             // null인 값들을 빼주기 위한 updateFromDto

        if(boardPostDto.getId() != null){   // 수정 글 로직. N+1문제 있음.
            if(!boardService.confirm(boardPostDto.getId(), user.getEmail())){
                return "error";
            }
        }

        boardService.save(userEmail, board);  // 글 저장 save

//        return "redirect:/board/list";
        return "board/form";
    }


//    @PostMapping("/form")
//    public ModelAndView form(ModelMap model, @Valid BoardPostDto boardPostDto, BindingResult bindingResult, HttpSession httpSession) throws Exception{
//        ModelAndView modelAndView = new ModelAndView();
//        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
//        modelAndView.setView(jsonView);
//
//        if(bindingResult.hasErrors()){      // 제목이 2글자 이하이거나 30자 이상인 경우 에러를 출력한다.
//            RedirectView redirectView = new RedirectView();
//            redirectView.setUrl("board/form :: info-form");
//
//            System.out.println(bindingResult);
//            model.addAttribute("result", "fail");
//            model.addAttribute("view", redirectView);
//            return modelAndView;
//        }
//
//        System.out.println(boardPostDto.getContent());
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//        String userEmail = user.getEmail();
//
//        Board board = boardPostMapper.toEntity(boardPostDto);           // mapstruct를 사용하여 Dto의 정보를 entity로 바꾸어준다.
//        boardPostMapper.updateFromDto(boardPostDto, board);             // null인 값들을 빼주기 위한 updateFromDto
//
//        boardService.save(userEmail, board);  // 글 저장 save
//        RedirectView redirectView = new RedirectView();
//        redirectView.setUrl("board/form");
//
//        model.addAttribute("result", "success");
//        model.addAttribute("view", redirectView);
////        return "redirect:/board/list";
//        return modelAndView;
//    }

//    @PostMapping("/form")
//    public String form(@Valid BoardPostDto boardPostDto, BindingResult bindingResult, HttpSession httpSession){
//        if(bindingResult.hasErrors()){      // 제목이 2글자 이하이거나 30자 이상인 경우 에러를 출력한다.
//            return "board/form";
//        }
//
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//        String userEmail = user.getEmail();
//
//        Board board = boardPostMapper.toEntity(boardPostDto);           // mapstruct를 사용하여 Dto의 정보를 entity로 바꾸어준다.
//        boardPostMapper.updateFromDto(boardPostDto, board);             // null인 값들을 빼주기 위한 updateFromDto
//
//        boardService.save(userEmail, board);  // 글 저장 save
//
//        return "redirect:/board/list";
//    }

    // 게시판의 내용 확인
    @GetMapping("/post")
    public String post(Model model, @RequestParam(required = false) Long id){
        if(id==null){
            model.addAttribute("boardPostDto", new BoardPostDto());
        }else{
            Board board = boardService.post(id);
            BoardPostDto boardPostDto = boardPostMapper.toDto(board);
            boardPostMapper.updateFromDto(boardPostDto, board);             // null인 값들을 빼주기 위한 updateFromDto 적용
            model.addAttribute("boardPostDto", boardPostDto);

//            List<LikeDto> likeDtos = likeMapper.toDtos(boardService.getLike(id));
////            if(likeDtos.contains())
//            model.addAttribute("like", likeDtos);

            List<ReplyDto> replyDtos = replyMapper.toDtos(boardService.getReply(id));
            model.addAttribute("reply", replyDtos);
        }
        return "board/post";
    }
}