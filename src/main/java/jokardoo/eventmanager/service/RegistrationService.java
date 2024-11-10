package jokardoo.eventmanager.service;

import jokardoo.eventmanager.domain.event.Event;
import jokardoo.eventmanager.domain.event.EventStatus;
import jokardoo.eventmanager.domain.event.eventRegistration.Registration;
import jokardoo.eventmanager.mapper.event.RegistrationModelToEntityMapper;
import jokardoo.eventmanager.repository.RegistrationRepository;
import jokardoo.eventmanager.service.utils.AuthenticationParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    private final RegistrationModelToEntityMapper registrationModelToEntityMapper;

    private final EventService eventService;

    private final AuthenticationParser authParser;

    public void registerUserOnEvent(Long eventId) {

        Registration registration = new Registration();
        registration.setEventId(eventId);
        registration.setUserId(authParser.getId());

        Event event = eventService.getById(registration.getEventId());

        checkRegisterUserOnEvent(event, registration);

        registrationRepository.save(registrationModelToEntityMapper.toEntity(registration));
        event.setOccupiedPlaces(event.getOccupiedPlaces() + 1);
        eventService.save(event);

    }

    public void cancelRegistration(Long eventId) {

        Long userId = authParser.getId();
        Registration registration = registrationModelToEntityMapper.toModel(registrationRepository.findByEventIdAndUserId(eventId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Event by id " + eventId +
                        " was not found!")));

        Event curEvent = eventService.getById(registration.getEventId());

        if (curEvent.getStatus().equals(EventStatus.FINISHED) ||
                curEvent.getStatus().equals(EventStatus.STARTED)) {
            throw new IllegalArgumentException("Event registration cannot be cancelled, because it started or finished.");
        }

            curEvent.setOccupiedPlaces(curEvent.getOccupiedPlaces() - 1);
        eventService.save(curEvent);

        registrationRepository.deleteById(registration.getId());

    }

    public List<Registration> getAllByCurrentUserId() {
        return registrationModelToEntityMapper.toModel(registrationRepository.findAllByUserId(authParser.getId()));
    }

    private void checkRegisterUserOnEvent(Event event, Registration registration) {
        if (registrationRepository
                .existsByEventIdAndUserId(registration.getEventId(), registration.getUserId())) {
            throw new IllegalArgumentException("This user is already registered on this event!");
        }
        if (event.getOccupiedPlaces() >= event.getMaxPlaces()) {
            throw new IllegalArgumentException("All places on Event is occupied!");
        }
        if (event.getStatus().equals(EventStatus.CANCELLED) || event.getStatus().equals(EventStatus.FINISHED)) {
            throw new IllegalArgumentException("This event is already cancelled or finished!");
        }
    }
}
