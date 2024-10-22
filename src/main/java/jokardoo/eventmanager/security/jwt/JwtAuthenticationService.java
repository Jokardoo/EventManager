package jokardoo.eventmanager.security.jwt;

import jokardoo.eventmanager.domain.user.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    private final JwtTokenManager tokenManager;
    private final AuthenticationManager authenticationManager;

    public String authenticateUser(SignUpRequest signUpRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signUpRequest.getLogin(),
                        signUpRequest.getPassword()
                )
        );


        return tokenManager.generateToken(signUpRequest.getLogin());
    }


}