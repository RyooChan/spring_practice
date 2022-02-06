package com.board.board.mapper.Board;

import com.board.board.domain.Board;
import com.board.board.dto.Board.BoardListDto;
import com.board.board.mapper.EntityMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardListMapper extends EntityMapper<BoardListDto, Board> {
    @Override
//    @Mapping(target = "userName", source = "user.name.value")
//    List<BoardListDto> toDtos(List<Board> board);
    @Mapping(target = "userName", source = "user.name")
    BoardListDto toDto(Board board);

}
