package jokardoo.eventmanager.controller;

import jakarta.validation.Valid;
import jokardoo.eventmanager.domain.user.SignUpRequest;
import jokardoo.eventmanager.domain.user.User;
import jokardoo.eventmanager.dto.mapper.user.UserMapper;
import jokardoo.eventmanager.dto.user.UserDto;
import jokardoo.eventmanager.security.jwt.JwtAuthenticationService;
import jokardoo.eventmanager.security.jwt.JwtResponse;
import jokardoo.eventmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtAuthenticationService jwtAuthenticationService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid SignUpRequest signUpRequest) {

        User user = new User();
        user.setLogin(signUpRequest.getLogin());
        user.setPasswordHash(signUpRequest.getPassword());


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper
                        .modelToDto(
                                userService.registerUser(user)
                        )
                );
    }


    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> auth(@RequestBody @Valid SignUpRequest signUpRequest) {

        JwtResponse response = new JwtResponse();
        response.setJwtToken(jwtAuthenticationService.authenticateUser(signUpRequest));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getById(@PathVariable(name = "userId") Long id) {

        return ResponseEntity.ok(userMapper.modelToDto(userService.getById(id)));
    }


}
