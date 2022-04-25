package com.board.board.service;

import com.board.board.domain.Board;
import com.board.board.dto.Board.BoardListDto;
import com.board.board.dto.Board.BoardSearchCondition;
import com.board.board.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;

    public Page<BoardListDto> findBoardList(BoardSearchCondition condition, Pageable pageable){
        return boardRepository.searchBoardListPage(condition, pageable);
    }

    public Board findBoard(Long id){
        return boardRepository.findById(id).orElse(null);
    }

    public Board saveBoard(Board board){
        return boardRepository.save(board);
    }

    public Board updateBoard(Board board, Long boardId){
        Optional<Board> findBoard = boardRepository.findById(boardId);
        findBoard.ifPresent(selectBoard->{
            selectBoard.setTitle(board.getTitle());
            selectBoard.setContent(board.getContent());
        });
        return findBoard.orElse(null);
    }

    public void deleteBoard(Long boardId){
        Optional<Board> findBoard = boardRepository.findById(boardId);
        findBoard.ifPresent(selectBoard->{
            selectBoard.setDeleted(true);
        });
    }

//    public void deleteBoard(Long boardId){
//        boardRepository.deleteById(boardId);
//    }
}
