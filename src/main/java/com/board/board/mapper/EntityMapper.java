package com.board.board.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EntityMapper <D, E> {
    E toEntity(D dto);
    D toDto(E entity);

    // Entity업데이트 시 null이 아닌 값만 업데이트 하도록 함.
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(D dto, @MappingTarget E entity);

    List<D> toDtos(List<E> entity);
}
