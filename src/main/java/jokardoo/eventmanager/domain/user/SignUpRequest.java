package jokardoo.eventmanager.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {

    @NotBlank(message = "login must be not blank")
    @Size(min = 3, message = "Login size should be bigger than 3")
    private String login;

    @NotBlank(message = "password should be not blank")
    private String password;

}
