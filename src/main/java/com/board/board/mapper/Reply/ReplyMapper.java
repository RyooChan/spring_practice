package com.board.board.mapper.Reply;

import com.board.board.domain.Reply;
import com.board.board.dto.reply.ReplyDto;
import com.board.board.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReplyMapper extends EntityMapper<ReplyDto, Reply> {
}
