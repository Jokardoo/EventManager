package jokardoo.eventmanager.service;

import jokardoo.eventmanager.domain.location.EventLocation;
import jokardoo.eventmanager.domain.location.EventLocationEntity;
import jokardoo.eventmanager.repository.EventLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventLocationService {

    private final EventLocationRepository eventLocationRepository;

    @Transactional(readOnly = true)
    public List<EventLocation> getAll() {
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

    @Transactional(readOnly = true)
    public EventLocation getById(Integer id) {

        if (!isEventLocationContains(id)) {
            throw new IllegalArgumentException("Event location with id " + id + " not found!");
        }

        EventLocationEntity locationEntity = eventLocationRepository.findById(id).get();

        EventLocation eventLocation = new EventLocation(
                locationEntity.getId(),
                locationEntity.getName(),
                locationEntity.getAddress(),
                locationEntity.getCapacity(),
                locationEntity.getDescription());

        return eventLocation;
    }

    @Transactional(readOnly = false)
    public void deleteById(Integer id) {

        if (!isEventLocationContains(id)) {
            throw new IllegalArgumentException("You can't delete event location with id = "  + id
                    + ", because is doesn't exist!");
        }

        eventLocationRepository.deleteById(id);
    }

    @Transactional
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



    public boolean isEventLocationContains(Integer id) {
        return eventLocationRepository.findById(id).isPresent() ? true : false;
    }
}
