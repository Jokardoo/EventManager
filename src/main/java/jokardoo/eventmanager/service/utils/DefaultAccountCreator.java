package jokardoo.eventmanager.service.utils;

import jokardoo.eventmanager.domain.user.UserEntity;
import jokardoo.eventmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAccountCreator{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;



    public void createAdminIfNotExists() {
        System.out.println("Create default admin");
        if (userRepository.findByLogin("admin").isEmpty()) {
            UserEntity admin = new UserEntity();
            admin.setRole("ADMIN");
            admin.setLogin("admin");
            admin.setPasswordHash(passwordEncoder.encode("admin"));

            userRepository.save(admin);
        }


    }

    public void createUserIfNotExists() {
        System.out.println("Create default user");
        if (userRepository.findByLogin("user").isEmpty()) {
            UserEntity user = new UserEntity();
            user.setRole("USER");
            user.setLogin("user");
            user.setPasswordHash(passwordEncoder.encode("user"));
            user.setAge(20);

            userRepository.save(user);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fullDefaultCheck() {
        createUserIfNotExists();
        createAdminIfNotExists();
    }
}
