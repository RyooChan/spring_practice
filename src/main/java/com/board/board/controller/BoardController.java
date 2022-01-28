package com.board.board.controller;

import com.board.board.domain.Board;
import com.board.board.dto.Board.BoardPostDto;
import com.board.board.mapper.Board.BoardPostMapper;
import com.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final BoardPostMapper boardPostMapper;

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
    @PostMapping("/form")
    public String form(@Valid BoardPostDto boardPostDto, BindingResult bindingResult, Authentication authentication){
        if(bindingResult.hasErrors()){      // 제목이 2글자 이하이거나 30자 이상인 경우 에러를 출력한다.
            return "board/form";
        }
        String username = authentication.getName();
        Board board = boardPostMapper.toEntity(boardPostDto);           // mapstruct를 사용하여 Dto의 정보를 entity로 바꾸어준다.
        boardPostMapper.updateFromDto(boardPostDto, board);             // null인 값들을 빼주기 위한 updateFromDto

        boardService.save(username, board);  // 글 저장 save

        return "redirect:/board/list";
    }

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
        }
        return "board/post";
    }
}
