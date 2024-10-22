package jokardoo.eventmanager;

import jokardoo.eventmanager.dto.mapper.location.EventLocationMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class EventmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventmanagerApplication.class, args);
	}

	@Bean
	public EventLocationMapper eventLocationMapper() {
		return new EventLocationMapper();
	}

//	@Bean
//	@Scope(scopeName = "singleton")
//	public Logger logger() {
//		return new Logger();
//	}
}
