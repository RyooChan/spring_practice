package com.board.board.repository;

import com.board.board.dto.Board.BoardListDto;
import com.board.board.dto.Board.BoardSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    Page<BoardListDto> searchBoardListPage(BoardSearchCondition condition, Pageable pageable);
}
