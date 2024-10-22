package jokardoo.eventmanager;

import jokardoo.eventmanager.dto.mapper.location.EventLocationMapper;
import jokardoo.eventmanager.security.jwt.JwtProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class EventManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventManagerApplication.class, args);
	}

	@Bean
	public EventLocationMapper eventLocationMapper() {
		return new EventLocationMapper();
	}


	@Bean
	public JwtProperty jwtProperty() {
		return new JwtProperty();
	}

	@Bean
	public Logger logger() {
		return LoggerFactory.getLogger("logger");
	}
}
