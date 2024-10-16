package jokardoo.eventmanager.exceptions;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorMessageResponse {

    private String message;

    private String detailedMessage;

    private LocalDateTime dateTime;

    public ErrorMessageResponse(String message, String detailedMessage, LocalDateTime dateTime) {
        this.message = message;
        this.detailedMessage = detailedMessage;
        this.dateTime = dateTime;
    }
}
