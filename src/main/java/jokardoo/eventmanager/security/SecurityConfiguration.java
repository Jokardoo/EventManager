package jokardoo.eventmanager.security;

import jokardoo.eventmanager.security.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomUserDetailsService customUserDetailsService;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(login -> login.disable())

                .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(authorizeHttpRequest ->
                        authorizeHttpRequest

                                .requestMatchers(HttpMethod.GET, "/locations")
                                .hasAnyAuthority("USER, ADMIN")

                                .requestMatchers(HttpMethod.DELETE, "/locations")
                                .hasAnyAuthority("ADMIN")

                                .requestMatchers(HttpMethod.GET, "/locations/{locationId}")
                                .hasAnyAuthority("ADMIN, USER")


                                .requestMatchers(HttpMethod.PUT, "/locations/{locationId}")
                                .hasAnyAuthority("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/locations")
                                .hasAnyAuthority("ADMIN")




                                .requestMatchers(HttpMethod.GET, "/users/{userId}")
                                .hasAnyAuthority("ADMIN")

                                .requestMatchers(HttpMethod.GET, "/swagger-ui/index.html#/")
                                .permitAll()

                                .requestMatchers(HttpMethod.POST, "/users")
                                .permitAll()

                                .requestMatchers(HttpMethod.POST, "/users/auth")
                                .permitAll()


                                .requestMatchers(HttpMethod.POST, "/events")
                                .hasAnyAuthority("USER")

                                .requestMatchers(HttpMethod.PUT, "/events")
                                .hasAnyAuthority("ADMIN", "USER")

                                .requestMatchers(HttpMethod.POST, "/events/search")
                                .hasAnyAuthority("ADMIN", "USER")

                                .requestMatchers(HttpMethod.GET, "/events/my")
                                .hasAnyAuthority("ADMIN", "USER")

                                .requestMatchers(HttpMethod.POST, "/events/registration")
                                .hasAnyAuthority("ADMIN", "USER")


                                .anyRequest().authenticated())
                 .addFilterBefore(jwtTokenFilter, AnonymousAuthenticationFilter.class)
                 .exceptionHandling((exceptionHandling) ->  exceptionHandling
                         .accessDeniedHandler(customAccessDeniedHandler)
                         .authenticationEntryPoint(authenticationEntryPoint))
                .build();
    }

    // Занимается аутентификацией пользователя
    @Bean
    public AuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
