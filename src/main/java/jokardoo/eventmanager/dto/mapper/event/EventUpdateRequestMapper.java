package jokardoo.eventmanager.dto.mapper.event;

import jokardoo.eventmanager.domain.event.Event;
import jokardoo.eventmanager.domain.event.EventUpdateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class EventUpdateRequestMapper {
    public Event toModel(EventUpdateRequestDto dto) {
        Event event = new Event();

        event.setName(dto.getName());
        event.setMaxPlaces(dto.getMaxPlaces());
        event.setDate(dto.getDate());

        event.setCost(dto.getCost());
        event.setDuration(dto.getDuration());
        event.setLocationId(dto.getLocationId());

        return event;
    }
}
