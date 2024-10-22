package jokardoo.eventmanager.domain.user;

import lombok.Data;



@Data
public class User {

    private Long id;

    private String login;

    private String passwordHash;

    private Role role;

    private Integer age;
}
