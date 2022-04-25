package com.board.board.repository.reply;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static com.board.board.domain.QReply.*;
import static com.board.board.domain.oauth.QUser.*;

public class ReplyRepositoryImpl implements ReplyRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    public ReplyRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Long deleteReplyBulk(Long boardId){
        return queryFactory
                .update(reply)
                .set(reply.isDeleted, true)
                .where(reply.board.isDeleted)
                .execute();
    }
}
