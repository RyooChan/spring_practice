package com.board.board.controller;

import com.board.board.domain.Board;
import com.board.board.dto.Board.BoardPostDto;
import com.board.board.dto.Board.BoardSaveDto;
import com.board.board.mapper.Board.BoardSaveMapper;
import com.board.board.repository.BoardRepository;
import com.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 나중에 DTO로 변경 예정 -> OAuth2방식을 사용할 예정이라 일단은 board에서 처리중..
    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 10) Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchText){
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    //
    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id==null){
            model.addAttribute("boardSaveDto", new BoardSaveDto());
        }else{
            BoardSaveDto boardSaveDto = boardService.postForm(id);
            model.addAttribute("boardSaveDto", boardSaveDto);
        }
        return "board/form";
    }

    // 게시판의 글을 저장하고, 에러가 있으면 이를 알려준다.
    @PostMapping("/form")
    public String form(@Valid BoardSaveDto boardSaveDto, BindingResult bindingResult, Authentication authentication){

        if(bindingResult.hasErrors()){
            return "board/form";
        }
        String username = authentication.getName();
        boardService.save(username, boardSaveDto);

        return "redirect:/board/list";
    }

    // 게시판의 내용 확인
    @GetMapping("/post")
    public String post(Model model, @RequestParam(required = false) Long id){
        if(id==null){
            model.addAttribute("boardPostDto", new BoardPostDto());
        }else{
            BoardPostDto boardPostDto = boardService.post(id);
            model.addAttribute("boardPostDto", boardPostDto);
        }
        return "board/post";
    }
}
