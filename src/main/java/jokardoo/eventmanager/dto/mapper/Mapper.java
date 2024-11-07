package jokardoo.eventmanager.dto.mapper;

import java.util.List;

// M - Model
// D - DTO
// E - Entity
public interface Mapper<M, D, E> {

    M dtoToModel(D dto);

    D modelToDto(M model);

    List<M> dtoToModel(List<D> dtoList);

    List<D> modelToDto(List<M> modelList);

    E modelToEntity(M model);

    M entityToModel(E entity);

    List<E> modelToEntity(List<M> modelList);

    List<M> entityToModel(List<E> entityList);


}
