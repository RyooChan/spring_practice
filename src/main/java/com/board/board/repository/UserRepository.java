package com.board.board.repository;

import com.board.board.domain.Board;
import com.board.board.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이런 경우 기존의 Lazy Fetch가 무시되고, 바로 Left Outer Join으로 검색할 수 있다.
    // 기존의 Fetch에서 발생 가능한 N+1 문제를 해결하기 위해 findAll을 새로 선언하여 EntityGraph를 진행해 준다.
    @EntityGraph(attributePaths = { "boards" })
    List<User> findAll();

    User findByUsername(String username);
}
