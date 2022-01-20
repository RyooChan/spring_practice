package com.board.board.mapper.Board;

import com.board.board.domain.Board;
import com.board.board.dto.Board.BoardPostDto;
import com.board.board.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoardPostMapper extends EntityMapper<BoardPostDto, Board> {
}
