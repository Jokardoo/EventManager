package jokardoo.eventmanager.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenManager {
    private final JwtProperty jwtProperty;

    public String generateToken(String login) {
        return Jwts
                .builder()
                .setSubject(login) //указываем, кому передается токен
                .signWith(Keys.hmacShaKeyFor(jwtProperty.getSecret().getBytes()))
//                .signWith(Keys.hmacShaKeyFor("dHN1eWl1b3l3YWV3ZXJ3ZHllcnVpYXdlaG5yeXdmamFzZGhuaWZub3lmd2V1aW9mYWVyd2NyYXNlc2RnYWdhZGdo".getBytes()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperty.getExpiration()))
//                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .compact();

    }

    public String getLoginFromToken(String jwt) {
        String login = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtProperty.getSecret().getBytes()))
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();

        return login;
    }
}
