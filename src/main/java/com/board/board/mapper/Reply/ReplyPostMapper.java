package com.board.board.mapper.Reply;

import com.board.board.domain.Reply;
import com.board.board.dto.reply.ReplyPostDto;
import com.board.board.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReplyPostMapper extends EntityMapper<ReplyPostDto, Reply> {

    @Override
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "boardId",source = "board.id")
    ReplyPostDto toDto(Reply reply);
}