package jokardoo.eventmanager.controller;

import jakarta.validation.Valid;
import jokardoo.eventmanager.domain.user.SignUpRequest;
import jokardoo.eventmanager.domain.user.User;
import jokardoo.eventmanager.dto.user.UserDto;
import jokardoo.eventmanager.mapper.user.UserModelToDtoMapper;
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
    private final UserModelToDtoMapper userModelToDtoMapper;
    private final JwtAuthenticationService jwtAuthenticationService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid SignUpRequest signUpRequest) {

        User user = new User();
        user.setLogin(signUpRequest.getLogin());
        user.setPasswordHash(signUpRequest.getPassword());


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userModelToDtoMapper
                        .toDto(
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

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getById(@PathVariable(name = "userId") Long id) {

        return ResponseEntity.ok(userModelToDtoMapper.toDto(userService.getById(id)));
    }


}
