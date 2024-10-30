package jokardoo.eventmanager.service.utils;

import jokardoo.eventmanager.domain.user.Role;
import jokardoo.eventmanager.domain.user.User;
import jokardoo.eventmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationParser {

    private final UserService userService;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return  (User) auth.getPrincipal();

    }

    public String getLogin() {
        return getCurrentUser().getLogin();
    }

    public Role getRole() {
        return getCurrentUser().getRole();
    }

    public Long getId() {
        return userService.getIdByLogin(getLogin());
    }
}
