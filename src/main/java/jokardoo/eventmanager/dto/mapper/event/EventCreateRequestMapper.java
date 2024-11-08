package jokardoo.eventmanager.dto.mapper.event;

import jokardoo.eventmanager.domain.event.Event;
import jokardoo.eventmanager.domain.event.EventCreateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class EventCreateRequestMapper {
    public Event toModel(EventCreateRequestDto dto) {
        Event newEvent = new Event();

        newEvent.setLocationId(dto.getLocationId());
        newEvent.setMaxPlaces(dto.getMaxPlaces());
        newEvent.setName(dto.getName());
        newEvent.setDate(dto.getDate());
        newEvent.setDuration(dto.getDuration());
        newEvent.setCost(dto.getCost());

        return newEvent;
    }
}
