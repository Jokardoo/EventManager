package jokardoo.eventmanager.service;

import jokardoo.eventmanager.domain.user.Role;
import jokardoo.eventmanager.domain.user.User;
import jokardoo.eventmanager.domain.user.UserEntity;
import jokardoo.eventmanager.exceptions.IncorrectRoleException;
import jokardoo.eventmanager.mapper.user.UserModelToEntityMapper;
import jokardoo.eventmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserModelToEntityMapper userModelToEntityMapper;


    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final BCryptPasswordEncoder passwordEncoder;


    public User getById(Long id) {
        logger.info("INFO: Get user by id request started.");


        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("User with id " + id + " not found!"));

        return userModelToEntityMapper.toModel(userEntity);
    }

    public User findByLogin(String login) {
        logger.info("INFO: Find user by login request started.");

        UserEntity userEntity = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + login + " not found!"));

        return userModelToEntityMapper.toModel(userEntity);
    }



    public User registerUser(User user) {
        logger.info("INFO: Registration user request started.");

        if (user.getLogin().equalsIgnoreCase("admin")) {
            logger.info("WARN: Try to save user with role ADMIN.");
            throw new IncorrectRoleException("User with the admin role cannot be created");
        }

        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            logger.error("WARN: Registration failed.");

            throw new IllegalArgumentException("User with username '"
            + user.getLogin() + "' already exists!");
        }

        User newUser = new User();

            newUser.setLogin(user.getLogin());
            newUser.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
            newUser.setRole(Role.USER);

            UserEntity createdUserEntity = userRepository.save(userModelToEntityMapper.toEntity(newUser));

            return userModelToEntityMapper.toModel(createdUserEntity);


    }

    public Long getIdByLogin(String login) {
        UserEntity userEntity =  userRepository.findByLogin(login).orElseThrow(
                () -> new IllegalArgumentException("User with login '" + login + "' not found!")
        );
        return userEntity.getId();
    }



}
