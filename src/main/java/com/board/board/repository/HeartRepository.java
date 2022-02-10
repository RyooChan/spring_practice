package com.board.board.repository;

import com.board.board.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    List<Heart> findAllByBoardId(long boardId);

    long findCountByBoardId(long boardId);


    Long countByBoardId(long boardId);


    Heart findByBoardIdAndUserId(long boardId, long userId);

    long deleteByBoardIdAndUserId(long boardId, long userId);
}
