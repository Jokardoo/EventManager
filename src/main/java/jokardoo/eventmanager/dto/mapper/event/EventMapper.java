package jokardoo.eventmanager.dto.mapper.event;

import jokardoo.eventmanager.domain.event.Event;
import jokardoo.eventmanager.dto.event.EventDto;
import jokardoo.eventmanager.dto.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component
//public class EventMapper implements Mapper<Event, EventDto> {
//
//    // TODO use mapstruct
//    @Override
//    public Event toEntity(EventDto dto) {
//
//        Event event = new Event();
//
//        event.setDate(dto.getDate());
//        event.setDuration(dto.getDuration());
//        event.setLocationId(dto.getLocationId());
//
//        event.setCost(dto.getCost());
//        event.setId(dto.getId());
//        event.setName(dto.getName());
//
//        event.setMaxPlaces(dto.getMaxPlaces());
//        event.setOccupiedPlaces(dto.getOccupiedPlaces());
//        event.setOwnerId(dto.getOwnerId());
//
//        event.setStatus(dto.getStatus());
//
//
//
//        return event;
//    }
//
//    @Override
//    public EventDto toDto(Event entity) {
//
//        EventDto dto = new EventDto();
//
//        dto.setDate(entity.getDate());
//        dto.setDuration(entity.getDuration());
//        dto.setLocationId(entity.getLocationId());
//
//        dto.setCost(entity.getCost());
//        dto.setId(entity.getId());
//        dto.setName(entity.getName());
//
//        dto.setMaxPlaces(entity.getMaxPlaces());
//        dto.setOccupiedPlaces(entity.getOccupiedPlaces());
//        dto.setOwnerId(entity.getOwnerId());
//
//        dto.setStatus(entity.getStatus());
//
//        return dto;
//    }
//
//    @Override
//    public List<Event> toEntity(List<EventDto> dtoList) {
//        List<Event> events = new ArrayList<>();
//
//        for (EventDto dto : dtoList) {
//            events.add(toEntity(dto));
//        }
//
//        return events;
//    }
//
//    @Override
//    public List<EventDto> toDto(List<Event> entityList) {
//        List<EventDto> eventDtoList = new ArrayList<>();
//
//        for (Event event : entityList) {
//            eventDtoList.add(toDto(event));
//        }
//
//        return eventDtoList;
//    }

//    public static void main(String[] args) {
//        Event event = new Event();
//        EventMapper mapper = new EventMapper();
//
//        event.setDate(new Date());
//        event.setDuration(60);
//        event.setLocationId(1);
//
//        event.setCost(1000);
//        event.setId(1);
//        event.setName("Meeting");
//
//        event.setMaxPlaces(5000);
//        event.setOccupiedPlaces(400);
//        event.setOwnerId(2);
//
//        event.setStatus(EventStatus.WAIT_START);
//
//        EventDto dto = mapper.toDto(event);
//        Event entity = mapper.toEntity(dto);
//
//        System.out.println("DTO - " + dto);
//        System.out.println("-------------");
//        System.out.println("Entity - " + entity);
//    }
//}
