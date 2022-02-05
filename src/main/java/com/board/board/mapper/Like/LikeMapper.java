package com.board.board.mapper.Like;

import com.board.board.domain.Like;
import com.board.board.dto.like.LikeDto;
import com.board.board.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LikeMapper extends EntityMapper<LikeDto, Like>  {
}
