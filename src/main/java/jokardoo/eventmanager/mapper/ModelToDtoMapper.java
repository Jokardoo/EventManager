package jokardoo.eventmanager.mapper;

import java.util.List;

public interface ModelToDtoMapper<M, D> {

    M toModel(D dto);

    D toDto(M model);

    List<M> toModel(List<D> dtoList);

    List<D> toDto(List<M> modelList);
}
