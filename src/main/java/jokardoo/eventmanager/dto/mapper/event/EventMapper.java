package jokardoo.eventmanager.dto.mapper.event;

import jokardoo.eventmanager.domain.event.Event;
import jokardoo.eventmanager.domain.event.EventEntity;
import jokardoo.eventmanager.domain.event.EventStatus;
import jokardoo.eventmanager.dto.event.EventDto;
import jokardoo.eventmanager.dto.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventMapper implements Mapper<Event, EventDto, EventEntity> {


    // TODO use mapstruct
    @Override
    public Event dtoToModel(EventDto dto) {

        Event event = new Event();

        event.setDate(dto.getDate());
        event.setDuration(dto.getDuration());
        event.setLocationId(dto.getLocationId());

        event.setCost(dto.getCost());
        event.setId(dto.getId());
        event.setName(dto.getName());

        event.setMaxPlaces(dto.getMaxPlaces());
        event.setOccupiedPlaces(dto.getOccupiedPlaces());
        event.setOwnerId(dto.getOwnerId());

        event.setStatus(EventStatus.valueOf(dto.getStatus()) );


        return event;
    }

    @Override
    public EventDto modelToDto(Event entity) {

        EventDto dto = new EventDto();

        dto.setDate(entity.getDate());
        dto.setDuration(entity.getDuration());
        dto.setLocationId(entity.getLocationId());

        dto.setCost(entity.getCost());
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        dto.setMaxPlaces(entity.getMaxPlaces());
        dto.setOccupiedPlaces(entity.getOccupiedPlaces());
        dto.setOwnerId(entity.getOwnerId());

        dto.setStatus(entity.getStatus().name());

        return dto;
    }

    @Override
    public List<Event> dtoToModel(List<EventDto> dtoList) {
        List<Event> events = new ArrayList<>();

        for (EventDto dto : dtoList) {
            events.add(dtoToModel(dto));
        }

        return events;
    }

    @Override
    public List<EventDto> modelToDto(List<Event> entityList) {
        List<EventDto> eventDtoList = new ArrayList<>();

        for (Event event : entityList) {
            eventDtoList.add(modelToDto(event));
        }

        return eventDtoList;
    }

    @Override
    public EventEntity modelToEntity(Event model) {
        EventEntity eventEntity = new EventEntity();

        eventEntity.setDate(model.getDate());
        eventEntity.setDuration(model.getDuration());
        eventEntity.setLocationId(model.getLocationId());

        eventEntity.setCost(model.getCost());
        eventEntity.setId(model.getId());
        eventEntity.setName(model.getName());

        eventEntity.setMaxPlaces(model.getMaxPlaces());
        eventEntity.setOccupiedPlaces(model.getOccupiedPlaces());
        eventEntity.setOwnerId(model.getOwnerId());

        eventEntity.setStatus(model.getStatus().name());

        return eventEntity;
    }

    @Override
    public Event entityToModel(EventEntity entity) {

        Event event = new Event();

        event.setDate(entity.getDate());
        event.setDuration(entity.getDuration());
        event.setLocationId(entity.getLocationId());

        event.setCost(entity.getCost());
        event.setId(entity.getId());
        event.setName(entity.getName());

        event.setMaxPlaces(entity.getMaxPlaces());
        event.setOccupiedPlaces(entity.getOccupiedPlaces());
        event.setOwnerId(entity.getOwnerId());


        event.setStatus(EventStatus.valueOf(entity.getStatus()));

        return event;
    }

    @Override
    public List<EventEntity> modelToEntity(List<Event> modelList) {
        List<EventEntity> entityList = new ArrayList<>();

        for (Event event : modelList) {
            entityList.add(modelToEntity(event));
        }

        return entityList;
    }

    @Override
    public List<Event> entityToModel(List<EventEntity> entityList) {
        List<Event> modelList = new ArrayList<>();

        for (EventEntity entity : entityList) {
            modelList.add(entityToModel(entity));
        }

        return modelList;
    }


}
