package jokardoo.eventmanager.service;

import jokardoo.eventmanager.domain.event.Event;
import jokardoo.eventmanager.domain.event.EventStatus;
import jokardoo.eventmanager.domain.event.eventRegistration.EventRegistration;
import jokardoo.eventmanager.domain.event.eventRegistration.EventRegistrationEntity;
import jokardoo.eventmanager.dto.mapper.event.EventRegistrationMapper;
import jokardoo.eventmanager.repository.EventRegistrationRepository;
import jokardoo.eventmanager.service.utils.AuthenticationParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventRegistrationService {

    private final EventRegistrationRepository eventRegistrationRepository;

    private final EventRegistrationMapper eventRegistrationMapper;

    private final EventService eventService;

    private final AuthenticationParser authParser;

    public EventRegistration registerUserOnEvent(Long eventId) {
        Long userId = authParser.getId();

        EventRegistration eventRegistration = new EventRegistration();
        eventRegistration.setEventId(eventId);
        eventRegistration.setUserId(authParser.getId());

        Event event = eventService.getById(eventId);
        //check

        if (eventRegistrationRepository.existsByEventIdAndUserId(eventId, userId)) {
            throw new IllegalArgumentException("This user is already registered on this event!");
        }
        if (event.getOccupiedPlaces() >= event.getMaxPlaces()) {
            throw new IllegalArgumentException("All places on Event is occupied!");
        }
        if (event.getStatus().equals(EventStatus.CANCELLED) || event.getStatus().equals(EventStatus.FINISHED)) {
            throw new IllegalArgumentException("This event is already cancelled or finished!");
        }

        EventRegistrationEntity createdRegistration = eventRegistrationRepository.save(eventRegistrationMapper.modelToEntity(eventRegistration));
        event.setOccupiedPlaces(event.getOccupiedPlaces() + 1);
        System.out.println(event.getOccupiedPlaces());
        eventService.save(event);

        return eventRegistrationMapper.entityToModel(createdRegistration);
    }

    public void cancelRegistration(Long eventId) {

        Long userId = authParser.getId();
        EventRegistration eventRegistration = eventRegistrationMapper.entityToModel(eventRegistrationRepository.findByEventIdAndUserId(eventId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Event by id " + eventId +
                        " was not found!")));

        Event curEvent = eventService.getById(eventRegistration.getEventId());

        if (curEvent.getStatus().equals(EventStatus.FINISHED) ||
                curEvent.getStatus().equals(EventStatus.STARTED)) {
            throw new IllegalArgumentException("Event registration cannot be cancelled, because it started or finished.");
        }

            curEvent.setOccupiedPlaces(curEvent.getOccupiedPlaces() - 1);
        eventService.save(curEvent);

        eventRegistrationRepository.deleteById(eventRegistration.getId());

    }

    public List<EventRegistration> getAllByCurrentUserId() {
        return eventRegistrationMapper.entityToModel(eventRegistrationRepository.findAllByUserId(authParser.getId()));
    }
}
