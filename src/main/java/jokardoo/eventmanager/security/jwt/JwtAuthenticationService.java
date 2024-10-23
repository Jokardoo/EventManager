package jokardoo.eventmanager.security.jwt;

import jokardoo.eventmanager.domain.user.SignUpRequest;
import jokardoo.eventmanager.domain.user.UserEntity;
import jokardoo.eventmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {
    //TEST FIELDS
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtTokenManager tokenManager;
    private final AuthenticationManager authenticationManager;

    public String authenticateUser(SignUpRequest signUpRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signUpRequest.getLogin(),
                        signUpRequest.getPassword()
                )
        );

        // TEST AUTHENTICATION

        UserEntity userEntity = userRepository
                .findByLogin(signUpRequest.getLogin())
                .orElseThrow(() -> new IllegalArgumentException("User with login " +
                        signUpRequest.getLogin() + " not found!"));

        if (userEntity.getLogin().equals(signUpRequest.getLogin())) {
            System.out.println("Right login");

            if (passwordEncoder.matches(signUpRequest.getPassword(), userEntity.getPasswordHash())) {
                System.out.println("Right password");
                return tokenManager.generateToken(signUpRequest.getLogin());
            }
        }

       throw new IllegalArgumentException("Incorrect data!");

//      END

//
//        return tokenManager.generateToken(signUpRequest.getLogin());
    }


}