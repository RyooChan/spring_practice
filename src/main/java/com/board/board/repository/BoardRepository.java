package com.board.board.repository;

import com.board.board.domain.Board;
import com.board.board.dto.Board.BoardPostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitleOrContent(String title, String content);

    // repository에서 DTO를 반환해도 괜찮을까?
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
