package com.board.board.controller;

import com.board.board.dto.Board.BoardListDto;
import com.board.board.dto.Board.BoardPostDto;
import com.board.board.dto.Board.BoardSearchCondition;
import com.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    /**
     * 게시글 List 출력
     * @param condition
     * @param pageable
     * @return
     */
    @GetMapping("/list")
    public Page<BoardListDto> searchBoardList(@RequestParam(required = false, defaultValue = "") BoardSearchCondition condition
                                   , @PageableDefault(size = 10) Pageable pageable){
        return boardService.searchBoardList(condition, pageable);
    }

//    @GetMapping("/{id}")
//    public ResponseBody<BoardPostDto> searchBoard(){
//
//    }

}
