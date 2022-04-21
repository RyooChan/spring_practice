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

import java.util.Optional;

@RestController
@RequestMapping("/api/boards/v2")
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
    public Page<BoardListDto> findBoardList(@RequestParam(required = false, defaultValue = "") BoardSearchCondition condition
                                   , @PageableDefault(size = 10) Pageable pageable){
        return boardService.findBoardList(condition, pageable);
    }

    @GetMapping("/post")
    public ResponseEntity<BoardPostDto> findBoard(@RequestParam(required = false) Long id){
        Board board = boardService.findBoard(id);
        if(!board.isDeleted()){
            BoardPostDto boardPostDto = boardPostMapper.toDto(board);
            return new ResponseEntity<>(boardPostDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/post")
    public ResponseEntity<BoardPostDto> saveBoard(BoardPostDto boardPostDto){
        Board board = boardPostMapper.toEntity(boardPostDto);
        Board result = boardService.saveBoard(board);
        BoardPostDto responseBoardInfo = boardPostMapper.toDto(result);
        return new ResponseEntity<>(responseBoardInfo, HttpStatus.OK);
    }

    @PutMapping("/post")
    public ResponseEntity<BoardPostDto> updateBoard(BoardPostDto boardPostDto, Long boardId){
        Board board = boardPostMapper.toEntity(boardPostDto);
        Board result = boardService.updateBoard(board, boardId);
        BoardPostDto responseBoardInfo = boardPostMapper.toDto(result);
        return new ResponseEntity<>(responseBoardInfo, HttpStatus.OK);
    }

    @DeleteMapping("/post")
    public ResponseEntity deleteBoard(Long boardId){
        boardService.deleteBoard(boardId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
