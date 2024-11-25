package jokardoo.eventmanager.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String login;

    private String passwordHash;

    private Role role;

    private Integer age;
}
