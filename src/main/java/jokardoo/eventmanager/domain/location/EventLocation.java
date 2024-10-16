package jokardoo.eventmanager.domain.location;

import lombok.Data;

@Data
public class EventLocation {

    private int id;

    private String name;

    private String address;

    private int capacity;

    private String description;

    public EventLocation() {

    }

    public EventLocation(int id, String name, String address, int capacity, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.description = description;
    }
}
