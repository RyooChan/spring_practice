package com.board.board.mapper.User;

import com.board.board.domain.User2;
import com.board.board.dto.User.UserDto;
import com.board.board.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User2> {

}
