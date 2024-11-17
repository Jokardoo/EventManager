package jokardoo.eventmanager.kafka;

import jokardoo.eventmanager.kafka.event.EventChangeNotification;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

    // Основной класс, который используется для отправки событий в кафку для записи
    // Все property, которые мы указали в application.property, будут находиться в этом классе kafkaProperties
    @Bean
    public KafkaTemplate<Long, EventChangeNotification> eventKafkaTemplate(KafkaProperties kafkaProperties)
    {

        var props = kafkaProperties
                .buildProducerProperties(
                        new DefaultSslBundleRegistry()
                );
        ProducerFactory<Long, EventChangeNotification> producerFactory = new DefaultKafkaProducerFactory<>(props);

        return new KafkaTemplate<>(producerFactory);
    }

}
