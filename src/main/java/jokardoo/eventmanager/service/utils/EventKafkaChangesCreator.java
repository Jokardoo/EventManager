package jokardoo.eventmanager.service.utils;

import jokardoo.eventmanager.domain.event.Event;
import jokardoo.eventmanager.domain.event.EventStatus;
import jokardoo.eventmanager.kafka.event.EventChangeNotification;
import jokardoo.eventmanager.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class EventKafkaChangesCreator {

    private final RegistrationRepository registrationRepository;

    private final AuthenticationParser authParser;


    public EventChangeNotification createEventChangeNotification(Event oldEvent, Event newEvent) {

        EventChangeNotification eventChangeNotification = getEventChangeNotificationWithImmutableUserData(oldEvent);

        eventChangeNotification.setEventNew(false);
        eventChangeNotification.setSubscribersIdList(registrationRepository.findAllSubscriberId(newEvent.getId()));

        updateOldValues(eventChangeNotification, oldEvent);
        updateNewValues(eventChangeNotification, newEvent);
        updateUserIdOnEventChangeNotification(eventChangeNotification);

        return eventChangeNotification;
    }

    public EventChangeNotification createNewEvent(Event newEvent) {

        EventChangeNotification eventChangeNotification =
                getEventChangeNotificationWithImmutableUserData(newEvent);

        eventChangeNotification.setEventNew(true);
        eventChangeNotification.setSubscribersIdList(new ArrayList<>());

        setStartDataForEventChangeNotification(eventChangeNotification);
        updateNewValues(eventChangeNotification, newEvent);
        updateUserIdOnEventChangeNotification(eventChangeNotification);

        return eventChangeNotification;
    }

    private EventChangeNotification getEventChangeNotificationWithImmutableUserData(Event event) {
        EventChangeNotification eventChangeNotification =
                EventChangeNotification.getInstanceWithDefaultFieldChanges();

        eventChangeNotification.setEventOwnerId(event.getOwnerId());
        eventChangeNotification.setEventId(event.getId());

        return eventChangeNotification;
    }

    private void setStartDataForEventChangeNotification(EventChangeNotification eventChangeNotification) {
        Event beginEvent = new Event();

        beginEvent.setStatus(EventStatus.WAIT_START);
        beginEvent.setName("-");
        beginEvent.setMaxPlaces(-1);
        beginEvent.setOccupiedPlaces(-1);
        beginEvent.setDate(LocalDateTime.now());
        beginEvent.setCost(-1);
        beginEvent.setDuration(-1);
        beginEvent.setLocationId((long) -1);

        updateOldValues(eventChangeNotification, beginEvent);
    }

    private void updateOldValues(EventChangeNotification eventChangeNotification, Event oldEvent) {
        eventChangeNotification.getName().setOldValue(oldEvent.getName());
        eventChangeNotification.getMaxPlaces().setOldValue(oldEvent.getMaxPlaces());
        eventChangeNotification.getOccupiedPlaces().setOldValue(oldEvent.getOccupiedPlaces());

        eventChangeNotification.getDate().setOldValue(oldEvent.getDate());
        eventChangeNotification.getCost().setOldValue(oldEvent.getCost());
        eventChangeNotification.getDuration().setOldValue(oldEvent.getDuration());

        eventChangeNotification.getLocationId().setOldValue(oldEvent.getLocationId());
        eventChangeNotification.getStatus().setOldValue(oldEvent.getStatus());
    }

    private void updateNewValues(EventChangeNotification eventChangeNotification, Event newEvent) {
        eventChangeNotification.getName().setNewValue(newEvent.getName());
        eventChangeNotification.getMaxPlaces().setNewValue(newEvent.getMaxPlaces());
        eventChangeNotification.getOccupiedPlaces().setNewValue(newEvent.getOccupiedPlaces());
        eventChangeNotification.getDate().setNewValue(newEvent.getDate());
        eventChangeNotification.getCost().setNewValue(newEvent.getCost());
        eventChangeNotification.getDuration().setNewValue(newEvent.getDuration());
        eventChangeNotification.getLocationId().setNewValue(newEvent.getLocationId());
        eventChangeNotification.getStatus().setNewValue(newEvent.getStatus());
    }

    private void updateUserIdOnEventChangeNotification(EventChangeNotification eventChangeNotification) {

        try {
            eventChangeNotification.setChangedUserId(authParser.getId());
        } catch (NullPointerException e) {
            eventChangeNotification.setChangedUserId(null);
        }

    }


    public EventChangeNotification cancelEventNotification(Event event) {

        EventChangeNotification eventChangeNotification = getEventChangeNotificationWithImmutableUserData(event);

        setCancelValuesFromEventChangeNotification(eventChangeNotification, event);


        return eventChangeNotification;
    }

    private void setCancelValuesFromEventChangeNotification(EventChangeNotification eventChangeNotification, Event event) {

        updateOldValues(eventChangeNotification, event);
        updateNewValues(eventChangeNotification, event);

        eventChangeNotification.setSubscribersIdList(registrationRepository.findAllSubscriberId(event.getId()));

        eventChangeNotification.getStatus().setOldValue(event.getStatus());
        eventChangeNotification.getStatus().setNewValue(EventStatus.CANCELLED);
        eventChangeNotification.setChangedUserId(authParser.getId());
    }
}
