package jokardoo.eventmanager.kafka.event;

import jokardoo.eventmanager.kafka.fieldsToChange.*;
import lombok.Data;

import java.util.List;

@Data
public class EventChangeNotification {

    private Long eventId;

    private Long userId;

    private Long eventOwnerId;

    private boolean isEventNew;


    private FieldChangeString name;

    private FieldChangeInteger maxPlaces;

    private FieldChangeInteger occupiedPlaces;

    private FieldChangeDateTime date;

    private FieldChangeInteger cost;

    private FieldChangeInteger duration;

    private FieldChangeLong locationId;

    private FieldChangeEventStatus status;


    private List<Long> subscribersIdList;


    public EventChangeNotification() {

    }

    public static EventChangeNotification getInstanceWithDefaultFieldChanges() {
        EventChangeNotification eventChangeNotification = new EventChangeNotification();

        eventChangeNotification.setCost(new FieldChangeInteger());
        eventChangeNotification.setName(new FieldChangeString());
        eventChangeNotification.setMaxPlaces(new FieldChangeInteger());

        eventChangeNotification.setOccupiedPlaces(new FieldChangeInteger());
        eventChangeNotification.setDate(new FieldChangeDateTime());
        eventChangeNotification.setDuration(new FieldChangeInteger());

        eventChangeNotification.setLocationId(new FieldChangeLong());
        eventChangeNotification.setStatus(new FieldChangeEventStatus());

        return eventChangeNotification;
    }

}
