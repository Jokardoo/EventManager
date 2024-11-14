package jokardoo.eventmanager.mapper.event;

import jokardoo.eventmanager.domain.event.eventRegistration.Registration;
import jokardoo.eventmanager.domain.event.eventRegistration.RegistrationEntity;
import jokardoo.eventmanager.mapper.ModelToEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RegistrationModelToEntityMapper extends ModelToEntityMapper<Registration, RegistrationEntity> {
}
