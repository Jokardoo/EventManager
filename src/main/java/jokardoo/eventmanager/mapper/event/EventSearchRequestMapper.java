package jokardoo.eventmanager.mapper.event;

import jokardoo.eventmanager.domain.event.EventSearchRequest;
import jokardoo.eventmanager.dto.event.EventSearchRequestDto;
import jokardoo.eventmanager.mapper.ModelToDtoMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventSearchRequestMapper extends ModelToDtoMapper<EventSearchRequest, EventSearchRequestDto> {


}
