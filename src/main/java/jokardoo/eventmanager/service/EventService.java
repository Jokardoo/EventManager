package jokardoo.eventmanager.service;

import jokardoo.eventmanager.domain.event.Event;
import jokardoo.eventmanager.domain.event.EventEntity;
import jokardoo.eventmanager.domain.event.EventSearchRequest;
import jokardoo.eventmanager.domain.event.EventStatus;
import jokardoo.eventmanager.domain.location.EventLocation;
import jokardoo.eventmanager.domain.user.Role;
import jokardoo.eventmanager.exceptions.IncorrectDateException;
import jokardoo.eventmanager.exceptions.IncorrectLocationCapacityException;
import jokardoo.eventmanager.mapper.event.EventModelToEntityMapper;
import jokardoo.eventmanager.repository.EventRepository;
import jokardoo.eventmanager.service.utils.AuthenticationParser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final Logger logger = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;

    private final AuthenticationParser authParser;

    private final EventModelToEntityMapper eventModelToEntityMapper;

    private final EventLocationService eventLocationService;

    public Event registerEvent(Event event) {
        logger.info("INFO: try to create event");

        checkEventFields(event);

        event.setOwnerId(authParser.getId());
        event.setStatus(EventStatus.WAIT_START);
        event.setOccupiedPlaces(0);


        EventEntity createdEventEntity = eventRepository
                .save(eventModelToEntityMapper.toEntity(
                        event
                ));

        Event createdEvent = eventModelToEntityMapper.toModel(createdEventEntity);

        logger.info("INFO: Event was created!");

        return createdEvent;
    }

    public Event getById(Long id) {
        logger.info("INFO: try to find event by id = " + id);
        return eventModelToEntityMapper.toModel(eventRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Event with id = " + id + " not found!")));
    }

    public void save(Event event) {

        eventRepository.save(eventModelToEntityMapper.toEntity(event));

    }

    public Event update(Event eventToUpdate, Long eventId) {

        logger.info("INFO: try to update event.");

        Event foundEvent = eventModelToEntityMapper
                .toModel(eventRepository
                                .findById(eventId)
                                .orElseThrow(() ->
                                        new IllegalArgumentException("Event with id = " + eventId + " not found!")));

        areOldEventCanBeUpdatedToNewEventFullCheck(foundEvent, eventToUpdate);

        updateOldEventFieldsToNewValues(foundEvent, eventToUpdate);

        EventEntity updatedEvent = eventRepository.save(eventModelToEntityMapper.toEntity(foundEvent));

        logger.info("INFO: Event update success.");

        return eventModelToEntityMapper.toModel(updatedEvent);
    }

    public void cancelEvent(Long id) {
        logger.info("INFO: Try to cancel event with id = " + id);

        Event event = eventModelToEntityMapper.toModel(eventRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Event with id = " + id + " not found!")));

        switch (event.getStatus()) {
            case CANCELLED -> throw new IllegalArgumentException("Event is already cancelled!");
            case FINISHED -> throw new IllegalArgumentException("Event is already finished!");
        }

        if (authParser.getRole().equals(Role.ADMIN)
                || authParser.getId().equals(event.getOwnerId())) {
            event.setStatus(EventStatus.CANCELLED);

            logger.info("INFO: The cancellation was successful");

        } else
            throw new IllegalStateException("To cancel an event, you must have the role admin or be the organizer of the event!");
    }

    public List<Event> getFilteredEvents(EventSearchRequest request) {
        List<EventEntity> entityList = eventRepository.searchByFilter(
                request.getDurationMax(),
                request.getDurationMin(),
                request.getDateStartBefore(),
                request.getDateStartAfter(),
                request.getPlacesMin(),
                request.getPlacesMax(),
                request.getLocationId(),
                request.getStatus(),
                request.getName(),
                request.getCostMin(),
                request.getCostMax()
        );

        return eventModelToEntityMapper.toModel(entityList);
    }


    public List<Event> getCurrentUserEvents() {
        return eventModelToEntityMapper.toModel(eventRepository.findByOwnerId(authParser.getId()));
    }

    public List<Event> findAll() {
        return eventModelToEntityMapper.toModel(eventRepository.findAll());
    }


    private void areOldEventCanBeUpdatedToNewEventFullCheck(Event oldEvent, Event newEvent) {
        checkUserAuthToUpdateEvent(oldEvent);
        areOldEventCanBeUpdatedToNewEventCheck(oldEvent, newEvent);
        checkEventFields(newEvent);
    }

    private void checkEventFields(Event event) {
        // Double to Integer
        EventLocation curLocation = eventLocationService.getById(Math.toIntExact(event.getLocationId()));

        if (curLocation.getCapacity() < event.getMaxPlaces()) {
            logger.warn("WARN: incorrect location capacity!");
            throw new IncorrectLocationCapacityException("This location cannot accommodate the number of people specified in the event");
        }


        LocalDateTime eventDate = event.getDate();

        if (eventDate.isBefore(LocalDateTime.now())) {
            logger.warn("WARN: Incorrect date exception!");
            throw new IncorrectDateException("The date of the event cannot be in the past tense!");
        }


    }

    private void checkUserAuthToUpdateEvent(Event event) {
        if (!authParser.getRole().equals(Role.ADMIN)) {
            if (!authParser.getId().equals(event.getOwnerId())) {
                throw new IllegalStateException("To update the event, you must be an admin or be a organizer of event!");
            }
        }
    }

    private void areOldEventCanBeUpdatedToNewEventCheck(Event oldEvent, Event newEvent) {
        if (!oldEvent.getStatus().equals(EventStatus.WAIT_START)) {
            logger.info("WARN: Event status is not 'WAIT_START'. Event can't be updated!");
            throw new IllegalArgumentException("You can't update this event, because it has already started, canceled or ended!");
        }

        if (oldEvent.getMaxPlaces() > newEvent.getMaxPlaces()) {
            throw new IllegalArgumentException("The maximum number of places in the update event must be equal to or greater, than it was before the update");
        }

        if (!oldEvent.getLocationId().equals(newEvent.getLocationId())) {
            EventLocation newEventLocation = eventLocationService.getById(Math.toIntExact(newEvent.getLocationId()));

            if (newEventLocation.getCapacity() < newEvent.getMaxPlaces()) {
                throw new IllegalArgumentException("The new location capacity is less, than planned in the event");
            }
        }
    }

    private void updateOldEventFieldsToNewValues(Event oldEvent, Event newEvent) {

        oldEvent.setName(newEvent.getName());
        oldEvent.setCost(newEvent.getCost());
        oldEvent.setDuration(newEvent.getDuration());

        oldEvent.setDate(newEvent.getDate());
        oldEvent.setMaxPlaces(newEvent.getMaxPlaces());
        oldEvent.setLocationId(newEvent.getLocationId());
    }
}