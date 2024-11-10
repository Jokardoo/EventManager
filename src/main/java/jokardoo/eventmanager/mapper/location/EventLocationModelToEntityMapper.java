package jokardoo.eventmanager.mapper.location;

import jokardoo.eventmanager.domain.location.EventLocation;
import jokardoo.eventmanager.domain.location.EventLocationEntity;
import jokardoo.eventmanager.mapper.ModelToEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventLocationModelToEntityMapper extends ModelToEntityMapper<EventLocation, EventLocationEntity> {
}
