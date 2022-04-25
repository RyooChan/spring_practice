package com.board.board.repository.reply;

import com.board.board.domain.Board;
import com.board.board.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long>, ReplyRepositoryCustom {
    List<Reply> findAllByBoardId(Long boardId);
    Reply findById(long id);
}
