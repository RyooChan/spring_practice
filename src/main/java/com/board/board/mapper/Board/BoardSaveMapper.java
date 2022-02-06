package com.board.board.mapper.Board;


import com.board.board.domain.Board;
import com.board.board.dto.Board.BoardSaveDto;
import com.board.board.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BoardSaveMapper extends EntityMapper<BoardSaveDto, Board> {
}
