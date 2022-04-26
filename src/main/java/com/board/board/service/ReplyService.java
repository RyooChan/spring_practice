package com.board.board.service;

import com.board.board.domain.Reply;
import com.board.board.repository.reply.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {
    private final ReplyRepository replyRepository;

    public List<Reply> findReplyList(Long boardId){
        return replyRepository.findAllByBoardId(boardId);
    }

    @Transactional
    public Reply saveReply(Reply reply){
        return replyRepository.save(reply);
    }

    @Transactional
    public Reply updateReply(Reply reply){
        Reply findReply = replyRepository.findById(reply.getId());
        findReply.setReplyContent(reply.getReplyContent());
        return reply;
    }

    @Transactional
    public void deleteReply(Long replyId){
        Optional<Reply> findReply = replyRepository.findById(replyId);
        findReply.ifPresent(selectReply->{
            selectReply.setDeleted(true);
        });
    }

    public long deleteReplyByBoard(Long boardId){
        return replyRepository.deleteReplyBulk(boardId);
    }

}
