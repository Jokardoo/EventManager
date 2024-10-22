package jokardoo.eventmanager.domain.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventCreateRequestDto {
    private String name;

    private int maxPlaces;

    private LocalDateTime date;

    private int cost;

    private int duration;

    private int locationId;
}
