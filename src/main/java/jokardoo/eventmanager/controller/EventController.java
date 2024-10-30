package jokardoo.eventmanager.controller;

import jakarta.validation.Valid;
import jokardoo.eventmanager.domain.event.*;
import jokardoo.eventmanager.dto.event.EventDto;
import jokardoo.eventmanager.dto.event.EventRegistrationDto;
import jokardoo.eventmanager.dto.mapper.event.EventMapper;
import jokardoo.eventmanager.dto.mapper.event.EventRegistrationMapper;
import jokardoo.eventmanager.service.EventRegistrationService;
import jokardoo.eventmanager.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    private final EventRegistrationService eventRegistrationService;

    private final EventMapper eventMapper;

    private final EventRegistrationMapper eventRegistrationMapper;

    @PostMapping
    public ResponseEntity<EventDto> createEvent(
            @Valid @RequestBody EventCreateRequestDto request) {


        Event newEvent = new Event();

        newEvent.setLocationId(request.getLocationId());
        newEvent.setMaxPlaces(request.getMaxPlaces());
        newEvent.setName(request.getName());
        newEvent.setDate(request.getDate());
        newEvent.setDuration(request.getDuration());
        newEvent.setCost(request.getCost());

        Event createdEvent = eventService.registerEvent(newEvent);

        return ResponseEntity.ok(eventMapper.modelToDto(createdEvent));
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteEvent(@RequestParam("eventId") Long eventId) {
        eventService.cancelEvent(eventId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> getEventByIf(@PathVariable(name = "eventId") Long eventId) {
        return ResponseEntity.ok(eventMapper.modelToDto(eventService.getById(eventId)));
    }

    @PutMapping
    public ResponseEntity<EventDto> updateEvent(@RequestParam("eventId") Long eventId, @RequestBody @Valid EventUpdateRequestDto eventUpdateRequestDto) {


        Event event = new Event();

        event.setName(eventUpdateRequestDto.getName());
        event.setMaxPlaces(eventUpdateRequestDto.getMaxPlaces());
        event.setDate(eventUpdateRequestDto.getDate());

        event.setCost(eventUpdateRequestDto.getCost());
        event.setDuration(eventUpdateRequestDto.getDuration());
        event.setLocationId(eventUpdateRequestDto.getLocationId());

        return ResponseEntity.ok(eventMapper.modelToDto(eventService.update(event, eventId)));
    }

    @PostMapping("/search")
    public ResponseEntity<List<EventDto>> findFilteredEvents(@RequestBody @Valid EventSearchRequestDto searchRequestDto) {
        List<Event> events = eventService.getFilteredEvents(searchRequestDto);
        return ResponseEntity.status(200).body(eventMapper.modelToDto(events));
    }

    @GetMapping("/my")
    public ResponseEntity<List<EventDto>> getCurrentUserEvents() {
        return ResponseEntity.ok(eventMapper.modelToDto(eventService.getCurrentUserEvents()));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registerUserOnEvent(@RequestParam("eventId") Long eventId) {
        eventRegistrationService.registerUserOnEvent(eventId);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/registration/cancel")
    public ResponseEntity<HttpStatus> cancelEventRegistration(@RequestParam Long eventId) {
        eventRegistrationService.cancelRegistration(eventId);

        return ResponseEntity.status(204).build();
    }

    @GetMapping("/registrations/my")
    public ResponseEntity<List<EventRegistrationDto>> getUserEventRegistrations() {
        return ResponseEntity.ok(eventRegistrationMapper.modelToDto(eventRegistrationService.getAllByCurrentUserId())) ;
    }

}
