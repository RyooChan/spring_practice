package com.board.board.repository;

import com.board.board.domain.Board;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom, QuerydslPredicateExecutor<Board> {

    List<Board> findByTitleOrContent(String title, String content);

//    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    // list출력 시 N+1해결을 위해 join fetch사용 -> 검색 도중 inner join으로 연결한다.
   @Query("select distinct e from Board e join fetch e.user")
//   @Query("select distinct board.id as id, board.title as title, board.content as content, user.email as user_id from Board as board inner join User as user on board.user.id=user.id")
   List<Board> findAllByTitleContainingOrContentContaining(String title, String content, Sort sort);

    Board findByUserId(long user_id);

}
