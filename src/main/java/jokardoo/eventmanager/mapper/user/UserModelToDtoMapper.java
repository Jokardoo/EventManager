package jokardoo.eventmanager.mapper.user;

import jokardoo.eventmanager.domain.user.User;
import jokardoo.eventmanager.dto.user.UserDto;
import jokardoo.eventmanager.mapper.ModelToDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserModelToDtoMapper extends ModelToDtoMapper<User, UserDto> {
}
