package jokardoo.eventmanager.mapper.event;

import jokardoo.eventmanager.domain.event.Event;
import jokardoo.eventmanager.domain.event.EventEntity;
import jokardoo.eventmanager.mapper.ModelToEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventModelToEntityMapper extends ModelToEntityMapper<Event, EventEntity> {
}
