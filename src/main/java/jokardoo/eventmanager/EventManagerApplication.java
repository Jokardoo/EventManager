package jokardoo.eventmanager;

import jokardoo.eventmanager.dto.mapper.location.EventLocationMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EventManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventManagerApplication.class, args);
	}

//	@Bean
//	public EventLocationMapper eventLocationMapper() {
//		return new EventLocationMapper();
//	}


}
