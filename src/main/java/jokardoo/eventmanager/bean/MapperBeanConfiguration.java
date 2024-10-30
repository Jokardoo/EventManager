package jokardoo.eventmanager.bean;

import jokardoo.eventmanager.dto.mapper.location.EventLocationMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MapperBeanConfiguration {
    @Bean
    public EventLocationMapper eventLocationMapper() {
        return new EventLocationMapper();
    }
}
