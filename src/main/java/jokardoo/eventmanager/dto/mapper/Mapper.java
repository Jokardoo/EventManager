package jokardoo.eventmanager.dto.mapper;

import java.util.List;

// E - entity
// D - DTO
public interface Mapper<E, D> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);

}
