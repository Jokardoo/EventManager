package jokardoo.eventmanager.security;

import jokardoo.eventmanager.domain.user.UserEntity;
import jokardoo.eventmanager.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        return User.withUsername(username)
                .password(userEntity.getPasswordHash())
                .authorities(userEntity.getRole())
                .build();
    }


    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
