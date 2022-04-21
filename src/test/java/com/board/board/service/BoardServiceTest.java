package com.board.board.service;

import com.board.board.controller.BoardController;
import com.board.board.domain.Board;
import com.board.board.domain.oauth.Role;
import com.board.board.domain.oauth.User;
import com.board.board.dto.Board.BoardListDto;
import com.board.board.dto.Board.BoardSearchCondition;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.oauth.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {
    @Autowired
    BoardController boardController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    @Test
    public void 삭제서비스테스트() throws Exception {
        //given
        User user = new User(1L, "찬", "fbcks97@naver.com", "picture", Role.GUEST);
        userRepository.save(user);
        for(int i=0; i<100; i++){
            Board board = new Board((long) i, "제목"+i, "내용"+i, user);
            boardRepository.save(board);
        }

        //when
        boardService.deleteBoard(4L);
        boardService.deleteBoard(15L);

        Board board = boardService.findBoard(5L);
        Board board2 = boardService.findBoard(15L);

        //then
        System.out.println("~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(board);
        System.out.println(board2);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~");
    }


}