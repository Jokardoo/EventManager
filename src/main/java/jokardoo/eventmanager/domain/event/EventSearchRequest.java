package jokardoo.eventmanager.domain.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventSearchRequest {
    private String name;

    private int placesMin;

    private int placesMax;

    private LocalDateTime dateStartBefore;

    private LocalDateTime dateStartAfter;

    private int costMin;

    private int costMax;

    private int durationMin;

    private int durationMax;

    private Long locationId;

    private EventStatus status;

    public EventSearchRequest(String name,
                              int placesMin,
                              int placesMax,
                              LocalDateTime dateStartBefore,
                              LocalDateTime dateStartAfter,
                              int costMin,
                              int costMax,
                              int durationMin,
                              int durationMax,
                              Long locationId,
                              EventStatus status) {
        this.name = name;
        this.placesMin = placesMin;
        this.placesMax = placesMax;
        this.dateStartBefore = dateStartBefore;
        this.dateStartAfter = dateStartAfter;
        this.costMin = costMin;
        this.costMax = costMax;
        this.durationMin = durationMin;
        this.durationMax = durationMax;
        this.locationId = locationId;
        this.status = status;
    }
}
