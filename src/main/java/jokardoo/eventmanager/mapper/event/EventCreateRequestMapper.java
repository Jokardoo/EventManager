package jokardoo.eventmanager.mapper.event;

import jokardoo.eventmanager.domain.event.Event;
import jokardoo.eventmanager.domain.event.EventCreateRequestDto;
import jokardoo.eventmanager.mapper.ModelToDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventCreateRequestMapper extends ModelToDtoMapper<Event, EventCreateRequestDto> {

}
