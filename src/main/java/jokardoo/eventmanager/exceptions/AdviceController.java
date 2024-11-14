package jokardoo.eventmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessageResponse> IllegalArgumentException(IllegalArgumentException e) {
        ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse(
                "Client error", e.getMessage(), LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessageResponse);
    }



    @ExceptionHandler(IncorrectRoleException.class)
    public ResponseEntity<ErrorMessageResponse> incorrectRoleException(IncorrectRoleException e) {
        ErrorMessageResponse errorMessageResponse =
                new ErrorMessageResponse("Authorization exception.",
                        e.getMessage(),
                        LocalDateTime.now());
        return ResponseEntity.status(400).body(errorMessageResponse);
    }

    @ExceptionHandler(UserCreatingException.class)
    public ResponseEntity<ErrorMessageResponse> userCreatingException(UserCreatingException e) {
        ErrorMessageResponse errorMessageResponse =
                new ErrorMessageResponse("User creating exception",
                        e.getMessage(),
                        LocalDateTime.now());
        return ResponseEntity.status(400).body(errorMessageResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> usernameNotFoundException(UsernameNotFoundException e) {
        ErrorMessageResponse errorMessageResponse =
                new ErrorMessageResponse("Username not found exception",
                        e.getMessage(),
                        LocalDateTime.now());
        return ResponseEntity.status(404).body(errorMessageResponse);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorMessageResponse> illegalStateException(IllegalStateException e) {
        ErrorMessageResponse errorMessageResponse =
                new ErrorMessageResponse("IllegalStateException",
                        e.getMessage(),
                        LocalDateTime.now());
        return ResponseEntity.status(401).body(errorMessageResponse);
    }

}
