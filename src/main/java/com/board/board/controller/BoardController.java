package com.board.board.controller;

import com.board.board.dto.Board.BoardListDto;
import com.board.board.dto.Board.BoardSearchCondition;
import com.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public Page<BoardListDto> searchBoardList(@RequestParam(required = false, defaultValue = "") BoardSearchCondition condition
                                   , @PageableDefault(size = 10) Pageable pageable){
        return boardService.searchBoardList(condition, pageable);
    }

}
