package com.board.board.mapper.User;

import com.board.board.domain.oauth.User;
import com.board.board.dto.oauth.SessionUser;
import com.board.board.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OauthUserMapper extends EntityMapper<SessionUser, User> {
}
