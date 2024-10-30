package jokardoo.eventmanager.service;

import jokardoo.eventmanager.domain.location.EventLocation;
import jokardoo.eventmanager.domain.location.EventLocationEntity;
import jokardoo.eventmanager.dto.mapper.location.EventLocationMapper;
import jokardoo.eventmanager.repository.EventLocationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventLocationService {
    private final Logger logger = LoggerFactory.getLogger(EventLocationService.class);

    private final EventLocationMapper eventLocationMapper;
    private final EventLocationRepository eventLocationRepository;


    public List<EventLocation> getAll() {
        logger.info("INFO: Get all Location request started.");
        List<EventLocation> eventLocationList = new ArrayList<>();

        for (EventLocationEntity locationEntity : eventLocationRepository.findAll()) {
            EventLocation eventLocation = new EventLocation(
                    locationEntity.getId(),
                    locationEntity.getName(),
                    locationEntity.getAddress(),
                    locationEntity.getCapacity(),
                    locationEntity.getDescription());
            eventLocationList.add(eventLocation);
        }
        return eventLocationList;
    }

    public EventLocation getById(Integer id) {

        logger.info("INFO: Get Location BY id request started.");

        EventLocationEntity locationEntity = eventLocationRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Location with id = " + id
                                + " not found!"));

        return new EventLocation(
                locationEntity.getId(),
                locationEntity.getName(),
                locationEntity.getAddress(),
                locationEntity.getCapacity(),
                locationEntity.getDescription());
    }

    public void deleteById(Integer id) {

        if (!eventLocationRepository.existsById(id)) {
            throw new IllegalArgumentException("You can't delete event location with id = " + id
                    + ", because it doesn't exist!");
        }
        eventLocationRepository.deleteById(id);
    }

    public void save(EventLocation eventLocation) {
        EventLocationEntity locationEntity = new EventLocationEntity(
                eventLocation.getId(),
                eventLocation.getName(),
                eventLocation.getAddress(),
                eventLocation.getCapacity(),
                eventLocation.getDescription()
        );
        eventLocationRepository.save(locationEntity);
    }



    public void update(Integer id, EventLocation eventLocation) {
        if (!eventLocationRepository.existsById(id)) {
            throw new IllegalArgumentException("You can't update event location with id = " + id
                    + ", because it doesn't exist!");
        }

        eventLocation.setId(id);

        save(eventLocation);
    }
}
