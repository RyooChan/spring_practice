package com.board.board.controller;

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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardControllerTest {
    @Autowired
    BoardController boardController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void 멤버페이징테스트() throws Exception {
        //given
        User user = new User(1L, "찬", "fbcks97@naver.com", "picture", Role.GUEST);
        userRepository.save(user);
        for(int i=0; i<100; i++){
            Board board = new Board((long) i, "제목"+i, "내용"+i, user);
            boardRepository.save(board);
        }

        BoardSearchCondition condition = new BoardSearchCondition();
        condition.setTitle("제목1");
        System.out.println(condition);

        //when

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<BoardListDto> results = boardController.searchBoardList(condition, pageRequest);

        System.out.println("~~~~~~~~~~~~~~~~~~~~~");
        //then
        for (BoardListDto result : results) {
            System.out.println(result);
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~");
    }
}