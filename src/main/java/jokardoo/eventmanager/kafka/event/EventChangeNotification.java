package jokardoo.eventmanager.kafka.event;

import jokardoo.eventmanager.domain.event.EventStatus;
import jokardoo.eventmanager.kafka.fieldsToChange.FieldChange;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventChangeNotification {

    private Long eventId;

    private Long changedUserId;

    private Long eventOwnerId;

    private boolean isEventNew;


    private FieldChange<String> name;

    private FieldChange<Integer> maxPlaces;

    private FieldChange<Integer> occupiedPlaces;

    private FieldChange<LocalDateTime> date;

    private FieldChange<Integer> cost;

    private FieldChange<Integer> duration;

    private FieldChange<Long> locationId;

    private FieldChange<EventStatus> status;


    private List<Long> subscribersIdList;


    public EventChangeNotification() {

    }

    public static EventChangeNotification getInstanceWithDefaultFieldChanges() {
        EventChangeNotification eventChangeNotification = new EventChangeNotification();

        eventChangeNotification.setCost(new FieldChange<>());
        eventChangeNotification.setName(new FieldChange<>());
        eventChangeNotification.setMaxPlaces(new FieldChange<>());

        eventChangeNotification.setOccupiedPlaces(new FieldChange<>());
        eventChangeNotification.setDate(new FieldChange<>());
        eventChangeNotification.setDuration(new FieldChange<>());

        eventChangeNotification.setLocationId(new FieldChange<>());
        eventChangeNotification.setStatus(new FieldChange<>());

        return eventChangeNotification;
    }

}
