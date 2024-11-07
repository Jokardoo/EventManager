package jokardoo.eventmanager.scheduler;

import jokardoo.eventmanager.domain.event.Event;
import jokardoo.eventmanager.domain.event.EventStatus;
import jokardoo.eventmanager.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@EnableScheduling
@Configuration
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
@RequiredArgsConstructor
public class EventScheduler {

    private final EventService eventService;

    @Scheduled(fixedRate = 60000)
    public void updateEventsStatus() {
        LocalDateTime now = LocalDateTime.now();

        List<Event> eventList = eventService.findAll();

        eventList
                .forEach(event -> {

                            if (event.getDate().isBefore(now) && event.getStatus().equals(EventStatus.WAIT_START)) {
                                event.setStatus(EventStatus.STARTED);
                            } else if (event.getDate().plusMinutes(event.getDuration()).isBefore(now) && event.getStatus().equals(EventStatus.STARTED)) {
                                event.setStatus(EventStatus.FINISHED);
                            }
                            eventService.save(event);
                        }
                );
    }

}
