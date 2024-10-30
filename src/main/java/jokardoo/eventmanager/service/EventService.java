package jokardoo.eventmanager.service;

import jokardoo.eventmanager.domain.event.Event;
import jokardoo.eventmanager.domain.event.EventEntity;
import jokardoo.eventmanager.domain.event.EventSearchRequestDto;
import jokardoo.eventmanager.domain.event.EventStatus;
import jokardoo.eventmanager.domain.location.EventLocation;
import jokardoo.eventmanager.domain.user.Role;
import jokardoo.eventmanager.dto.event.EventDto;
import jokardoo.eventmanager.dto.mapper.event.EventMapper;
import jokardoo.eventmanager.exceptions.IncorrectDateException;
import jokardoo.eventmanager.exceptions.IncorrectLocationCapacityException;
import jokardoo.eventmanager.repository.EventRegistrationRepository;
import jokardoo.eventmanager.repository.EventRepository;
import jokardoo.eventmanager.service.utils.AuthenticationParser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final Logger logger = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;

    private final EventRegistrationRepository eventRegistrationRepository;

    private final UserService userService;

    private final AuthenticationParser authParser;

    private final EventMapper eventMapper;

    private final EventLocationService eventLocationService;

    public Event registerEvent(Event event) {
        logger.info("INFO: try to create event");

        // check
        checkEventFields(event);
        // All right

        EventDto eventDto = new EventDto();

        eventDto.setName(event.getName());
        eventDto.setCost(event.getCost());
        eventDto.setDate(event.getDate());

        eventDto.setDuration(event.getDuration());
        eventDto.setLocationId(event.getLocationId());
        eventDto.setMaxPlaces(event.getMaxPlaces());
        eventDto.setOwnerId(authParser.getId());

        eventDto.setOccupiedPlaces(0);

        eventDto.setStatus("WAIT_START");


        EventEntity createdEvent = eventRepository
                .save(eventMapper.modelToEntity(
                        eventMapper.dtoToModel(eventDto)
                ));

        logger.info("INFO: Event was created!");

        return eventMapper.entityToModel(createdEvent);
    }

    public Event getById(Long id) {
        logger.info("INFO: try to find event by id = " + id);
        return eventMapper.entityToModel(eventRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Event with id = " + id + " not found!")));
    }

    public Event save(Event event) {
        return eventMapper.entityToModel(eventRepository.save(eventMapper.modelToEntity(event)));
    }

    public Event update(Event eventToUpdate, Long eventId) {

        logger.info("INFO: try to update event.");

        if (!authParser.getRole().equals(Role.ADMIN)
                || authParser.getId().equals(eventToUpdate.getId())) {
            throw new IllegalStateException("To update the event, you must be an admin or be a organizer of event!");
        }

        Event foundEvent = eventMapper
                .entityToModel(
                        eventRepository
                                .findById(eventId)
                                .orElseThrow(() ->
                                        new IllegalArgumentException("Event with id = " + eventId + " not found!"))
                );

        // If the event has already started, canceled, or ended
        if (!foundEvent.getStatus().equals(EventStatus.WAIT_START)) {
            logger.info("WARN: Event status is not 'WAIT_START'. Event can't be updated!");
            throw new IllegalArgumentException("You can't update this event, because it has already started, canceled or ended!");
        }

        if (foundEvent.getMaxPlaces() > eventToUpdate.getMaxPlaces()) {
            throw new IllegalArgumentException("The maximum number of places in the update event must be equal to or greater, than it was before the update");
        }

        // If we change location
        if (foundEvent.getLocationId() != eventToUpdate.getLocationId()) {
            EventLocation newEventLocation = eventLocationService.getById(Math.toIntExact(eventToUpdate.getLocationId()));

            if (newEventLocation.getCapacity() < eventToUpdate.getMaxPlaces()) {
                throw new IllegalArgumentException("The new location capacity is less, than planned in the event");
            }
        }

        checkEventFields(eventToUpdate);

        foundEvent.setName(eventToUpdate.getName());
        foundEvent.setCost(eventToUpdate.getCost());
        foundEvent.setDuration(eventToUpdate.getDuration());

        foundEvent.setDate(eventToUpdate.getDate());
        foundEvent.setMaxPlaces(eventToUpdate.getMaxPlaces());
        foundEvent.setLocationId(eventToUpdate.getLocationId());

        EventEntity updatedEvent = eventRepository.save(eventMapper.modelToEntity(foundEvent));

        logger.info("INFO: Event update success.");

        return eventMapper.entityToModel(updatedEvent);
    }

    public void cancelEvent(Long id) {
        logger.info("INFO: Try to cancel event with id = " + id);

        Event event = eventMapper.entityToModel(eventRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Event with id = " + id + " not found!")));

        switch (event.getStatus()) {
            case CANCELLED -> {
                throw new IllegalArgumentException("Event is already cancelled!");
            }
            case FINISHED -> {
                throw new IllegalArgumentException("Event is already finished!");
            }
        }

        if (authParser.getRole().equals(Role.ADMIN)
                || authParser.getId().equals(event.getOwnerId())) {
            event.setStatus(EventStatus.CANCELLED);
            eventRepository.save(eventMapper.modelToEntity(event));

            logger.info("INFO: The cancellation was successful");
        } else
            throw new IllegalStateException("To cancel an event, you must have the role admin or be the organizer of the event!");
    }

    public List<Event> getFilteredEvents(EventSearchRequestDto requestDto) {
        List<EventEntity> entityList = eventRepository.searchByFilter(
                requestDto.getDurationMax(),
                requestDto.getDurationMin(),
                requestDto.getDateStartBefore(),
                requestDto.getDateStartAfter(),
                requestDto.getPlacesMin(),
                requestDto.getPlacesMax(),
                requestDto.getLocationId(),
                requestDto.getStatus(),
                requestDto.getName(),
                requestDto.getCostMin(),
                requestDto.getCostMax()
        );

        return eventMapper.entityToModel(entityList);
    }


    public List<Event> getCurrentUserEvents() {
        return eventMapper.entityToModel(eventRepository.findByOwnerId(authParser.getId()));
    }

    private void checkEventFields(Event event) {
        // Double to Integer
        EventLocation curLocation = eventLocationService.getById(Math.toIntExact(event.getLocationId()));

        // Capacity check
        if (curLocation.getCapacity() < event.getMaxPlaces()) {
            logger.warn("WARN: incorrect location capacity!");
            throw new IncorrectLocationCapacityException("This location cannot accommodate the number of people specified in the event");
        }

        // Date check
        try {
            LocalDateTime eventDate = event.getDate();
            LocalDateTime curDate = LocalDateTime.now();

            if (eventDate.isBefore(curDate)) {
                logger.warn("WARN: Incorrect date exception!");
                throw new IncorrectDateException("The date of the event cannot be in the past tense!");
            }
        } catch (Exception e) {
            logger.warn("WARN: Incorrect date exception!");
            throw new IncorrectDateException("Incorrect date form from event.");
        }

    }

    @Scheduled(fixedRate = 60000)
    @Async
    public void updateEventsStatus() {
        LocalDateTime now = LocalDateTime.now();

        List<Event> eventList = eventMapper.entityToModel(eventRepository.findAll());

        eventList
                .stream()
                .forEach(event -> {

                            if (event.getDate().isBefore(now) && event.getStatus().equals(EventStatus.WAIT_START)) {
                                event.setStatus(EventStatus.STARTED);
                            } else if (event.getDate().plusMinutes(event.getDuration()).isBefore(now) && event.getStatus().equals(EventStatus.STARTED)) {
                                event.setStatus(EventStatus.FINISHED);
                            }
                            save(event);
                        }
                );
    }

}
