package jokardoo.eventmanager.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jokardoo.eventmanager.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenManager {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String login, User user) {

        Claims claims = Jwts.claims().setSubject(login);

        claims.put("role", user.getRole().name());

        return Jwts
                .builder()
                .setClaims(claims) //указываем, кому передается токен
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .compact();

    }

    public String getLoginFromToken(String jwt) {
        String login = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();

        return login;
    }

    public String getRoleFromToken(String jwt) {
        String role = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .get("role", String.class);

        return role;
    }

}
