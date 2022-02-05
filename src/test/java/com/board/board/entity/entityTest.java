package com.board.board.entity;

import com.board.board.domain.oauth.User;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.oauth.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@DataJpaTest
@RunWith(SpringRunner.class)
//@Sql(scripts = "/entitygraph-data.sql")
public class entityTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void 되냐(){
//        Board board = boardRepository.findByUserId()
        List<User> users = userRepository.findAll();
    }
}
