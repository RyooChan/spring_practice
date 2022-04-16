package com.board.board.repository;

import com.board.board.domain.Board;
//import com.board.board.domain.QBoard;
//import com.board.board.domain.oauth.QUser;
import com.board.board.dto.Board.BoardListDto;
import com.board.board.dto.Board.BoardSearchCondition;
import com.board.board.dto.Board.QBoardListDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.board.board.domain.QBoard.*;
import static com.board.board.domain.oauth.QUser.*;
import static com.board.board.dto.Board.QBoardListDto.*;

public class BoardRepositoryImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    public BoardRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BoardListDto> searchBoardListPage(BoardSearchCondition condition, Pageable pageable){
        List<BoardListDto> content = queryFactory
                .select(new QBoardListDto(
                        board.id.as("id")
                        , board.title.as("title")
                        , user.name.as("userName")
                ))
                .from(board)
                .leftJoin(board.user, user)
                .where(
                        boardIdEq(condition.getId())
                        , boardTitleEq(condition.getTitle())
                        , boardUserNameEq(condition.getUserName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Board> countBoard = queryFactory
                .select(board)
                .from(board)
                .leftJoin(board.user, user)
                .where(
                        boardIdEq(condition.getId())
                        , boardTitleEq(condition.getTitle())
                        , boardUserNameEq(condition.getUserName())
                );

        return PageableExecutionUtils.getPage(content, pageable, countBoard::fetchCount);
    }

    private BooleanExpression boardIdEq(Long id){
        return id != null ? board.id.eq(id) : null;
    }

    private BooleanExpression boardTitleEq(String title){
        return StringUtils.hasText(title) ? board.title.eq(title) : null;
    }

    private BooleanExpression boardUserNameEq(String userName){
        return StringUtils.hasText(userName) ? user.name.eq(userName) : null;
    }

}
