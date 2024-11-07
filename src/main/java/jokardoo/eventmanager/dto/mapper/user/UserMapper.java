package jokardoo.eventmanager.dto.mapper.user;

import jokardoo.eventmanager.domain.user.Role;
import jokardoo.eventmanager.domain.user.User;
import jokardoo.eventmanager.domain.user.UserEntity;
import jokardoo.eventmanager.dto.mapper.Mapper;
import jokardoo.eventmanager.dto.user.UserDto;
import jokardoo.eventmanager.exceptions.IncorrectRoleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserDto, UserEntity> {

    // TODO mapstruct
    @Override
    public User dtoToModel(UserDto dto) {
        User user = new User();

        user.setLogin(dto.getLogin());
        user.setPasswordHash(dto.getPassword());
        user.setAge(dto.getAge());

        return user;
    }

    @Override
    public UserDto modelToDto(User user) {
        UserDto dto = new UserDto();

        dto.setLogin(user.getLogin());
        dto.setPassword(user.getPasswordHash());
        dto.setAge(user.getAge());

        return dto;
    }

    @Override
    public List<User> dtoToModel(List<UserDto> dtoList) {

        List<User> userList = new ArrayList<>();

        for (UserDto dto : dtoList) {
            userList.add(dtoToModel(dto));
        }

        return userList;
    }

    @Override
    public List<UserDto> modelToDto(List<User> modelList) {
        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : modelList) {
            userDtoList.add(modelToDto(user));
        }

        return userDtoList;
    }

    @Override
    public UserEntity modelToEntity(User model) {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(model.getId());
        userEntity.setLogin(model.getLogin());
        userEntity.setRole(model.getRole().name());
        userEntity.setPasswordHash(model.getPasswordHash());
        userEntity.setAge(model.getAge());

        return userEntity;
    }

    @Override
    public User entityToModel(UserEntity entity) {
        User user = new User();

        user.setId(entity.getId() != null ? entity.getId() : null);
        user.setLogin(entity.getLogin());
        user.setPasswordHash(entity.getPasswordHash());
        user.setAge(entity.getAge());

        try {
            user.setRole(Role.valueOf(entity.getRole()));
        }
        catch (Exception e) {
            throw new IncorrectRoleException("Incorrect role name");
        }

        return user;
    }

    @Override
    public List<UserEntity> modelToEntity(List<User> modelList) {

        List<UserEntity> userEntityList = new ArrayList<>();

        for (User user : modelList) {
            userEntityList.add(modelToEntity(user));
        }
            return userEntityList;
    }

    @Override
    public List<User> entityToModel(List<UserEntity> entityList) {

        List<User> userList = new ArrayList<>();

        for (UserEntity entity : entityList) {
            userList.add(entityToModel(entity));
        }

        return userList;
    }

}
