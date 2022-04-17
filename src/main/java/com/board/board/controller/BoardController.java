package com.board.board.controller;

import com.board.board.domain.Board;
import com.board.board.dto.Board.BoardListDto;
import com.board.board.dto.Board.BoardPostDto;
import com.board.board.dto.Board.BoardSearchCondition;
import com.board.board.mapper.Board.BoardPostMapper;
import com.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardPostMapper boardPostMapper;

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

    @GetMapping("/post")
    public ResponseEntity<BoardPostDto> searchBoard(@RequestParam(required = false) Long id){
        Board board = boardService.searchBoard(id);
        BoardPostDto boardPostDto = boardPostMapper.toDto(board);
        return new ResponseEntity<>(boardPostDto, HttpStatus.OK);
    }

}
