package jokardoo.eventmanager.exceptions;

// if UserDto have String role != ROLE or ADMIN

public class IncorrectRoleException extends RuntimeException {
    public IncorrectRoleException(String message) {
        super(message);
    }
}
