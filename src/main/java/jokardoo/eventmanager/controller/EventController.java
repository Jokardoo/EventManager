package jokardoo.eventmanager.controller;

import jakarta.validation.Valid;
import jokardoo.eventmanager.domain.event.Event;
import jokardoo.eventmanager.domain.event.EventCreateRequestDto;
import jokardoo.eventmanager.dto.event.EventSearchRequestDto;
import jokardoo.eventmanager.domain.event.EventUpdateRequestDto;
import jokardoo.eventmanager.dto.event.EventDto;
import jokardoo.eventmanager.dto.event.RegistrationDto;
import jokardoo.eventmanager.mapper.event.*;
import jokardoo.eventmanager.service.RegistrationService;
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

    private final RegistrationService registrationService;

    private final EventCreateRequestMapper eventCreateRequestMapper;
    private final EventUpdateRequestMapper eventUpdateRequestMapper;
    private final EventSearchRequestMapper eventSearchRequestMapper;

    private final EventModelToDtoMapper eventModelToDtoMapper;

    private final RegistrationModelToDtoMapper registrationModelToDtoMapper;


    @PostMapping
    public ResponseEntity<EventDto> createEvent(
            @Valid @RequestBody EventCreateRequestDto request) {

        Event createdEvent = eventService
                .registerEvent(
                        eventCreateRequestMapper
                                .toModel(request)
                );

        return ResponseEntity.ok(eventModelToDtoMapper.toDto(createdEvent));
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteEvent(@RequestParam("eventId") Long eventId) {
        eventService.cancelEvent(eventId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> getEventById(@PathVariable(name = "eventId") Long eventId) {
        return ResponseEntity.ok(eventModelToDtoMapper.toDto(eventService.getById(eventId)));
    }

    @PutMapping
    public ResponseEntity<EventDto> updateEvent(@RequestParam("eventId") Long eventId, @RequestBody @Valid EventUpdateRequestDto eventUpdateRequestDto) {

        Event event = eventUpdateRequestMapper.toModel(eventUpdateRequestDto);

        return ResponseEntity.ok(eventModelToDtoMapper.toDto(eventService.update(event, eventId)));
    }

    @PostMapping("/search")
    public ResponseEntity<List<EventDto>> findFilteredEvents(@RequestBody @Valid EventSearchRequestDto searchRequestDto) {

        List<Event> events = eventService.getFilteredEvents(eventSearchRequestMapper.toModel(searchRequestDto));

        return ResponseEntity.status(200).body(eventModelToDtoMapper.toDto(events));
    }

    @GetMapping("/my")
    public ResponseEntity<List<EventDto>> getCurrentUserEvents() {
        return ResponseEntity.ok(eventModelToDtoMapper.toDto(eventService.getCurrentUserEvents()));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registerUserOnEvent(@RequestParam("eventId") Long eventId) {
        registrationService.registerUserOnEvent(eventId);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/registration/cancel")
    public ResponseEntity<HttpStatus> cancelEventRegistration(@RequestParam Long eventId) {
        registrationService.cancelRegistration(eventId);

        return ResponseEntity.status(204).build();
    }

    @GetMapping("/registrations/my")
    public ResponseEntity<List<RegistrationDto>> getUserEventRegistrations() {
        return ResponseEntity.ok(registrationModelToDtoMapper.toDto(registrationService.getAllByCurrentUserId()));
    }

}
