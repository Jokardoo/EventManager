package jokardoo.eventmanager.mapper.event;

import jokardoo.eventmanager.domain.event.eventRegistration.Registration;
import jokardoo.eventmanager.dto.event.RegistrationDto;
import jokardoo.eventmanager.mapper.ModelToDtoMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationModelToDtoMapper extends ModelToDtoMapper<Registration, RegistrationDto> {
}
