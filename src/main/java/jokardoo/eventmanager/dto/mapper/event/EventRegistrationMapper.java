package jokardoo.eventmanager.dto.mapper.event;

import jokardoo.eventmanager.domain.event.eventRegistration.EventRegistration;
import jokardoo.eventmanager.domain.event.eventRegistration.EventRegistrationEntity;
import jokardoo.eventmanager.dto.event.EventRegistrationDto;
import jokardoo.eventmanager.dto.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventRegistrationMapper implements Mapper<EventRegistration, EventRegistrationDto, EventRegistrationEntity> {
    @Override
    public EventRegistration dtoToModel(EventRegistrationDto dto) {
        EventRegistration eventRegistration = new EventRegistration();

        eventRegistration.setEventId(dto.getEventId());
        eventRegistration.setUserId(dto.getUserId());

        return eventRegistration;
    }

    @Override
    public EventRegistrationDto modelToDto(EventRegistration model) {
        EventRegistrationDto eventRegistrationDto = new EventRegistrationDto();

        eventRegistrationDto.setEventId(model.getEventId());
        eventRegistrationDto.setUserId(model.getUserId());

        return eventRegistrationDto;
    }

    @Override
    public List<EventRegistration> dtoToModel(List<EventRegistrationDto> dtoList) {
        List<EventRegistration> eventRegistrationList = new ArrayList<>();

        for (EventRegistrationDto eventRegistrationDto : dtoList) {
            eventRegistrationList.add(dtoToModel(eventRegistrationDto));
        }

        return eventRegistrationList;
    }

    @Override
    public List<EventRegistrationDto> modelToDto(List<EventRegistration> modelList) {
        List<EventRegistrationDto> dtoList = new ArrayList<>();

        for (EventRegistration eventRegistration : modelList) {
            dtoList.add(modelToDto(eventRegistration));
        }

        return dtoList;
    }

    @Override
    public EventRegistrationEntity modelToEntity(EventRegistration model) {
        EventRegistrationEntity entity = new EventRegistrationEntity();

        entity.setEventId(model.getEventId());
        entity.setUserId(model.getUserId());
        entity.setId(model.getId());

        return entity;
    }

    @Override
    public EventRegistration entityToModel(EventRegistrationEntity entity) {
        EventRegistration eventRegistration = new EventRegistration();

        eventRegistration.setEventId(entity.getEventId());
        eventRegistration.setId(entity.getId());
        eventRegistration.setUserId(entity.getUserId());

        return eventRegistration;
    }

    @Override
    public List<EventRegistrationEntity> modelToEntity(List<EventRegistration> modelList) {
        ArrayList<EventRegistrationEntity> entities = new ArrayList<>();

        for (EventRegistration eventRegistration : modelList) {
            entities.add(modelToEntity(eventRegistration));
        }

        return entities;
    }

    @Override
    public List<EventRegistration> entityToModel(List<EventRegistrationEntity> entityList) {
        ArrayList<EventRegistration> registrations = new ArrayList<>();

        for (EventRegistrationEntity entity : entityList) {
            registrations.add(entityToModel(entity));
        }

        return registrations;
    }
}
