package jokardoo.eventmanager.mapper.user;

import jokardoo.eventmanager.domain.user.User;
import jokardoo.eventmanager.domain.user.UserEntity;
import jokardoo.eventmanager.mapper.ModelToEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserModelToEntityMapper extends ModelToEntityMapper<User, UserEntity> {
}
