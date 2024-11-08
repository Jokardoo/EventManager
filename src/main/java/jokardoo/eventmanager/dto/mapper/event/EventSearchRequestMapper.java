package jokardoo.eventmanager.dto.mapper.event;

import jokardoo.eventmanager.domain.event.EventSearchRequest;
import jokardoo.eventmanager.dto.event.EventSearchRequestDto;
import org.springframework.stereotype.Component;

@Component
public class EventSearchRequestMapper {
    public EventSearchRequest toModel(EventSearchRequestDto dto) {
        return new EventSearchRequest(dto.getName(),
                dto.getPlacesMin(),
                dto.getPlacesMax(),
                dto.getDateStartBefore(),
                dto.getDateStartAfter(),
                dto.getCostMin(),
                dto.getCostMax(),
                dto.getDurationMin(),
                dto.getDurationMax(),
                dto.getLocationId(),
                dto.getStatus());
    }
}
