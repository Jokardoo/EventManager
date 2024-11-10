package jokardoo.eventmanager.mapper.location;

import jokardoo.eventmanager.domain.location.EventLocation;
import jokardoo.eventmanager.dto.location.EventLocationDto;
import jokardoo.eventmanager.mapper.ModelToDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventLocationModelToDtoMapper extends ModelToDtoMapper<EventLocation, EventLocationDto> {
}
