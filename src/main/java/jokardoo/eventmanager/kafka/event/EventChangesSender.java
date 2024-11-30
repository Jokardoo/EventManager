package jokardoo.eventmanager.kafka.event;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventChangesSender {

    private static final Logger logger = LoggerFactory.getLogger(EventChangesSender.class);

    private final KafkaTemplate<Long, EventChangeNotification> kafkaTemplate;

    public void sendEvent(EventChangeNotification eventChanges) {

        if (hasActualDataFromEvent(eventChanges)) {
            logger.info("Sending Event: {}", eventChanges);

            var result =
                    kafkaTemplate.send("event-topic", eventChanges.getEventId(), eventChanges);

            result.thenAccept(sendResult -> logger.info("Send successful"));
        }


    }

    private boolean hasActualDataFromEvent(EventChangeNotification eventChanges) {

        if (eventChanges.getName().hasChanges()
                || eventChanges.getMaxPlaces().hasChanges()
                || eventChanges.getOccupiedPlaces().hasChanges()
                || eventChanges.getDate().hasChanges()
                || eventChanges.getCost().hasChanges()
                || eventChanges.getDuration().hasChanges()
                || eventChanges.getLocationId().hasChanges()
                || eventChanges.getStatus().hasChanges())
            return true;

        else return false;


    }

}
