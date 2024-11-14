package jokardoo.eventmanager.mapper;

import java.util.List;

public interface ModelToEntityMapper<M, E> {
    M toModel(E entity);

    E toEntity(M model);

    List<M> toModel(List<E> entityList);

    List<E> toEntity(List<M> modelList);
}
