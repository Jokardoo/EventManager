package jokardoo.eventmanager.domain.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event implements Cloneable {

    private Long id;

    private String name;

    private Long ownerId;

    private Integer maxPlaces;

    private Integer occupiedPlaces;

    private LocalDateTime date;

    private Integer cost;

    // в минутах
    private Integer duration;

    private Long locationId;

    private EventStatus status;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
