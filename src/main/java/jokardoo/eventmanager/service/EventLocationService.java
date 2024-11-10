package jokardoo.eventmanager.service;

import jokardoo.eventmanager.domain.location.EventLocation;
import jokardoo.eventmanager.domain.location.EventLocationEntity;
import jokardoo.eventmanager.mapper.location.EventLocationModelToEntityMapper;
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

    private final EventLocationModelToEntityMapper eventLocationModelToEntityMapper;
    private final EventLocationRepository eventLocationRepository;


    public List<EventLocation> getAll() {
        logger.info("INFO: Get all Location request started.");
        List<EventLocation> eventLocationList = new ArrayList<>();

        for (EventLocationEntity locationEntity : eventLocationRepository.findAll()) {
            eventLocationList.add(eventLocationModelToEntityMapper.toModel(locationEntity));
        }
        return eventLocationList;
    }

    public EventLocation getById(Integer id) {

        logger.info("INFO: Get Location BY id request started.");

        EventLocationEntity locationEntity = eventLocationRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Location with id = " + id
                                + " not found!"));

        return eventLocationModelToEntityMapper.toModel(locationEntity);
    }

    public void deleteById(Integer id) {

        if (!eventLocationRepository.existsById(id)) {
            throw new IllegalArgumentException("You can't delete event location with id = " + id
                    + ", because it doesn't exist!");
        }
        eventLocationRepository.deleteById(id);
    }

    public void save(EventLocation eventLocation) {
        eventLocationRepository.save(eventLocationModelToEntityMapper.toEntity(eventLocation));
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
