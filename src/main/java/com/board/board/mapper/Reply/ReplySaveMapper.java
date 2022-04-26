package com.board.board.mapper.Reply;

import com.board.board.domain.Reply;
import com.board.board.dto.reply.ReplySaveDto;
import com.board.board.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReplySaveMapper extends EntityMapper<ReplySaveDto, Reply> {
    @Override
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "boardId",target = "board.id")
    Reply toEntity(ReplySaveDto replySaveDto);
}
