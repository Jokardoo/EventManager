package jokardoo.eventmanager.dto.mapper.event;

import jokardoo.eventmanager.domain.event.eventRegistration.Registration;
import jokardoo.eventmanager.domain.event.eventRegistration.RegistrationEntity;
import jokardoo.eventmanager.dto.event.RegistrationDto;
import jokardoo.eventmanager.dto.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegistrationMapper implements Mapper<Registration, RegistrationDto, RegistrationEntity> {
    @Override
    public Registration dtoToModel(RegistrationDto dto) {
        Registration registration = new Registration();

        registration.setEventId(dto.getEventId());
        registration.setUserId(dto.getUserId());

        return registration;
    }

    @Override
    public RegistrationDto modelToDto(Registration model) {
        RegistrationDto registrationDto = new RegistrationDto();

        registrationDto.setEventId(model.getEventId());
        registrationDto.setUserId(model.getUserId());

        return registrationDto;
    }

    @Override
    public List<Registration> dtoToModel(List<RegistrationDto> dtoList) {
        List<Registration> registrationList = new ArrayList<>();

        for (RegistrationDto registrationDto : dtoList) {
            registrationList.add(dtoToModel(registrationDto));
        }

        return registrationList;
    }

    @Override
    public List<RegistrationDto> modelToDto(List<Registration> modelList) {
        List<RegistrationDto> dtoList = new ArrayList<>();

        for (Registration registration : modelList) {
            dtoList.add(modelToDto(registration));
        }

        return dtoList;
    }

    @Override
    public RegistrationEntity modelToEntity(Registration model) {
        RegistrationEntity entity = new RegistrationEntity();

        entity.setEventId(model.getEventId());
        entity.setUserId(model.getUserId());
        entity.setId(model.getId());

        return entity;
    }

    @Override
    public Registration entityToModel(RegistrationEntity entity) {
        Registration registration = new Registration();

        registration.setEventId(entity.getEventId());
        registration.setId(entity.getId());
        registration.setUserId(entity.getUserId());

        return registration;
    }

    @Override
    public List<RegistrationEntity> modelToEntity(List<Registration> modelList) {
        ArrayList<RegistrationEntity> entities = new ArrayList<>();

        for (Registration registration : modelList) {
            entities.add(modelToEntity(registration));
        }

        return entities;
    }

    @Override
    public List<Registration> entityToModel(List<RegistrationEntity> entityList) {
        ArrayList<Registration> registrations = new ArrayList<>();

        for (RegistrationEntity entity : entityList) {
            registrations.add(entityToModel(entity));
        }

        return registrations;
    }
}
