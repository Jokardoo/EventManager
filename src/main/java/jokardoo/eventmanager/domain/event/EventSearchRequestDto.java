package jokardoo.eventmanager.domain.event;

import lombok.Data;

@Data
public class EventSearchRequestDto {

    private String name;

    private int placesMin;

    private int placesMax;

    private String dateStartBefore;

    private String dateStartAfter;

    private int costMin;

    private int costMax;

    private int durationMin;

    private int durationMax;

    private int locationId;

    private String status;


}
