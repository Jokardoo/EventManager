package jokardoo.eventmanager.domain.event;

import lombok.Data;

import java.util.Date;

@Data
public class Event {

    private int id;

    private String name;

    private int ownerId;

    private int maxPlaces;

    private int occupiedPlaces;

    private Date date;

    private double cost;

    // в минутах
    private int duration;

    private int locationId;

    private EventStatus status;

}
