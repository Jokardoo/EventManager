package jokardoo.eventmanager.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UserDto {

    @NotBlank(message = "login must be not blank")
    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Positive(message = "age should be positive!")
    private Integer age;
}
