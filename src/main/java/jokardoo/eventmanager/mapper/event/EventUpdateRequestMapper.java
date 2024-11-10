package jokardoo.eventmanager.mapper.event;

import jokardoo.eventmanager.domain.event.Event;
import jokardoo.eventmanager.domain.event.EventUpdateRequestDto;
import jokardoo.eventmanager.mapper.ModelToDtoMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventUpdateRequestMapper extends ModelToDtoMapper<Event, EventUpdateRequestDto> {

}
