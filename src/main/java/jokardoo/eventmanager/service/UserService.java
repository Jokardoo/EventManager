package jokardoo.eventmanager.service;

import jakarta.annotation.PostConstruct;
import jakarta.validation.UnexpectedTypeException;
import jokardoo.eventmanager.domain.user.Role;
import jokardoo.eventmanager.domain.user.SignUpRequest;
import jokardoo.eventmanager.domain.user.User;
import jokardoo.eventmanager.domain.user.UserEntity;
import jokardoo.eventmanager.dto.mapper.user.UserMapper;
import jokardoo.eventmanager.dto.user.UserDto;
import jokardoo.eventmanager.exceptions.IncorrectRoleException;
import jokardoo.eventmanager.exceptions.UserCreatingException;
import jokardoo.eventmanager.repository.UserRepository;
import jokardoo.eventmanager.service.utils.DefaultAccountCreator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final DefaultAccountCreator accountCreator;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final BCryptPasswordEncoder passwordEncoder;

//    @Transactional(readOnly = true)
//    public List<User> getAll() {
//        logger.info("INFO: Get all user request started.");
//        return userMapper.entityToModel(userRepository.findAll());
//    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        logger.info("INFO: Get user by id request started.");

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("User with id " + id + " not found!"));

        return userMapper.entityToModel(userEntity);
    }


//    public void deleteById(Long id) {
//        logger.info("INFO: Delete user by id request started.");
//        userRepository.deleteById(id);
//        logger.info("INFO: Delete request was successful.");
//    }

    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        logger.info("INFO: Find user by login request started.");

        UserEntity userEntity = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + login + " not found!"));

        return userMapper.entityToModel(userEntity);
    }



    public UserDto registerUser(SignUpRequest signUpRequest) {
        logger.info("INFO: Registration user request started.");

        if (signUpRequest.getLogin().equalsIgnoreCase("admin")) {
            logger.info("WARN: Try to save user with role ADMIN.");
            throw new IncorrectRoleException("User with the admin role cannot be created");
        }

        if (userRepository.findByLogin(signUpRequest.getLogin()).isPresent()) {
            logger.error("WARN: Registration failed.");

            throw new IllegalArgumentException("User with username '"
            + signUpRequest.getLogin() + "' already exists!");
        }

        User newUser = new User();

        try {
            newUser.setLogin(signUpRequest.getLogin());
            newUser.setPasswordHash(passwordEncoder.encode(signUpRequest.getPassword()));
            newUser.setRole(Role.USER);

            UserEntity createdUserEntity = userRepository.save(userMapper.modelToEntity(newUser));
            User createdUser = userMapper.entityToModel(createdUserEntity);

            return userMapper.modelToDto(createdUser);

        } catch (UnexpectedTypeException e) {
            throw new UserCreatingException("Invalid data has been entered. Login should be unique!");
        }

    }

    @PostConstruct
    private void createDefaultAccounts() {
        logger.info("Creating default accounts...");

        accountCreator.createAdminIfNotExists();
        accountCreator.createUserIfNotExists();
    }

}
