package com.board.board.mapper.Heart;

import com.board.board.domain.Board;
import com.board.board.domain.Heart;
import com.board.board.dto.Board.BoardListDto;
import com.board.board.dto.Heart.HeartDto;
import com.board.board.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HeartMapper extends EntityMapper<HeartDto, Heart>  {
    @Override
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "boardId", source = "board.id")
    HeartDto toDto(Heart heart);

    @Override
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "boardId",target = "board.id")
    Heart toEntity(HeartDto heartDto);
}
