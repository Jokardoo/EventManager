package jokardoo.eventmanager.mapper.event;

import jokardoo.eventmanager.domain.event.Event;
import jokardoo.eventmanager.dto.event.EventDto;
import jokardoo.eventmanager.mapper.ModelToDtoMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface EventModelToDtoMapper extends ModelToDtoMapper<Event, EventDto> {

}
