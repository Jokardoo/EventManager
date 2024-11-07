package jokardoo.eventmanager.dto.mapper.location;

import jokardoo.eventmanager.domain.location.EventLocation;
import jokardoo.eventmanager.domain.location.EventLocationEntity;
import jokardoo.eventmanager.dto.location.EventLocationDto;
import jokardoo.eventmanager.dto.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventLocationMapper implements Mapper<EventLocation, EventLocationDto, EventLocationEntity> {

    // TODO mapstruct
    @Override
    public EventLocation dtoToModel(EventLocationDto dto) {
        EventLocation eventLocation = new EventLocation();

        eventLocation.setId(dto.getId());
        eventLocation.setAddress(dto.getAddress());
        eventLocation.setName(dto.getName());

        eventLocation.setDescription(dto.getDescription());
        eventLocation.setCapacity(dto.getCapacity());

        return eventLocation;
    }

    @Override
    public EventLocationDto modelToDto(EventLocation entity) {
        EventLocationDto eventLocationDto = new EventLocationDto();

        eventLocationDto.setId(entity.getId());
        eventLocationDto.setAddress(entity.getAddress());
        eventLocationDto.setName(entity.getName());

        eventLocationDto.setDescription(entity.getDescription());
        eventLocationDto.setCapacity(entity.getCapacity());

        return eventLocationDto;
    }

    @Override
    public List<EventLocation> dtoToModel(List<EventLocationDto> dtoList) {
        List<EventLocation> eventLocationList = new ArrayList<>();

        for (EventLocationDto dto : dtoList) {
            eventLocationList.add(dtoToModel(dto));
        }
        return eventLocationList;
    }

    @Override
    public List<EventLocationDto> modelToDto(List<EventLocation> entityList) {
        List<EventLocationDto> eventLocationDtoList = new ArrayList<>();

        for (EventLocation eventLocation : entityList) {
            eventLocationDtoList.add(modelToDto(eventLocation));
        }
        return eventLocationDtoList;
    }

    //TODO реализовать
    @Override
    public EventLocationEntity modelToEntity(EventLocation model) {
        return null;
    }

    @Override
    public EventLocation entityToModel(EventLocationEntity entity) {
        return null;
    }

    @Override
    public List<EventLocationEntity> modelToEntity(List<EventLocation> modelList) {
        return null;
    }

    @Override
    public List<EventLocation> entityToModel(List<EventLocationEntity> entityList) {
        return null;
    }
}
