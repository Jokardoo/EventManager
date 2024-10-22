package jokardoo.eventmanager.service.utils;

import jokardoo.eventmanager.domain.user.UserEntity;
import jokardoo.eventmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAccountCreator {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void createAdminIfNotExists() {
        if (userRepository.findByLogin("admin").isEmpty()) {
            UserEntity admin = new UserEntity();
            admin.setRole("ADMIN");
            admin.setLogin("admin");
            admin.setPasswordHash(passwordEncoder.encode("admin"));

            userRepository.save(admin);
        }


    }

    public void createUserIfNotExists() {
        if (userRepository.findByLogin("user").isEmpty()) {
            UserEntity user = new UserEntity();
            user.setRole("USER");
            user.setLogin("user");
            user.setPasswordHash(passwordEncoder.encode("user"));
            user.setAge(20);

            userRepository.save(user);
        }
    }
}
