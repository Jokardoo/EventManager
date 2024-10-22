package jokardoo.eventmanager.dto.mapper.location;

import jokardoo.eventmanager.domain.location.EventLocation;
import jokardoo.eventmanager.dto.location.EventLocationDto;
import jokardoo.eventmanager.dto.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

public class EventLocationMapper implements Mapper<EventLocation, EventLocationDto> {

    // TODO mapstruct
    @Override
    public EventLocation toEntity(EventLocationDto dto) {
        EventLocation eventLocation = new EventLocation();

        eventLocation.setId(dto.getId());
        eventLocation.setAddress(dto.getAddress());
        eventLocation.setName(dto.getName());

        eventLocation.setDescription(dto.getDescription());
        eventLocation.setCapacity(dto.getCapacity());

        return eventLocation;
    }

    @Override
    public EventLocationDto toDto(EventLocation entity) {
        EventLocationDto eventLocationDto = new EventLocationDto();

        eventLocationDto.setId(entity.getId());
        eventLocationDto.setAddress(entity.getAddress());
        eventLocationDto.setName(entity.getName());

        eventLocationDto.setDescription(entity.getDescription());
        eventLocationDto.setCapacity(entity.getCapacity());

        return eventLocationDto;
    }

    @Override
    public List<EventLocation> toEntity(List<EventLocationDto> dtoList) {
        List<EventLocation> eventLocationList = new ArrayList<>();

        for (EventLocationDto dto : dtoList) {
            eventLocationList.add(toEntity(dto));
        }
        return eventLocationList;
    }

    @Override
    public List<EventLocationDto> toDto(List<EventLocation> entityList) {
        List<EventLocationDto> eventLocationDtoList = new ArrayList<>();

        for (EventLocation eventLocation : entityList) {
            eventLocationDtoList.add(toDto(eventLocation));
        }
        return eventLocationDtoList;
    }
}
