package com.board.board.service;

import com.board.board.controller.BoardController;
import com.board.board.controller.ReplyController;
import com.board.board.domain.Board;
import com.board.board.domain.Reply;
import com.board.board.domain.oauth.Role;
import com.board.board.domain.oauth.User;
import com.board.board.dto.Board.BoardListDto;
import com.board.board.dto.reply.ReplyPostDto;
import com.board.board.dto.reply.ReplySaveDto;
import com.board.board.mapper.Reply.ReplySaveMapper;
import com.board.board.repository.board.BoardRepository;
import com.board.board.repository.oauth.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReplyServiceTest {
    @Autowired
    BoardController boardController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ReplyController replyController;

    @Autowired
    BoardService boardService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    ReplySaveMapper replySaveMapper;

    @Test
    public void 댓글테스트 () throws Exception {
        //given
        User user = new User(1L, "찬", "fbcks97@naver.com", "picture", Role.GUEST);
        userRepository.save(user);

        for(int i=0; i<100; i++){
            Board board = new Board((long) i, "제목"+i, "내용"+i, user);
            boardRepository.save(board);
        }

        //when

        long boardIdForReply = 3L;

        for(int i=0; i<10; i++){
            ReplySaveDto replySaveDto = new ReplySaveDto((long)i, "내용", user.getId(), boardIdForReply);
            replyController.saveReply(replySaveDto);
        }
        List<ReplyPostDto> body = replyController.findReplyList(boardIdForReply).getBody();

        //then
        System.out.println("~~~~~~~~~~~~~~~~~~~~~");
        for (ReplyPostDto result : body) {
            System.out.println(result);
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~");

    }


}