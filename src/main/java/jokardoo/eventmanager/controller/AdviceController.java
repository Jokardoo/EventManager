package jokardoo.eventmanager.controller;

import jokardoo.eventmanager.exceptions.ErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessageResponse>  IllegalArgumentException(IllegalArgumentException e) {
        ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse(
                "Client error", e.getMessage(), LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessageResponse);
    }
}
