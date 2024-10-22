package jokardoo.eventmanager.controller;

import jakarta.validation.Valid;
import jokardoo.eventmanager.domain.location.EventLocation;
import jokardoo.eventmanager.dto.location.EventLocationDto;
import jokardoo.eventmanager.dto.mapper.location.EventLocationMapper;
import jokardoo.eventmanager.service.EventLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final EventLocationMapper eventLocationMapper;
    private final EventLocationService eventLocationService;

    @Autowired
    public LocationController(EventLocationMapper eventLocationMapper, EventLocationService eventLocationService) {
        this.eventLocationMapper = eventLocationMapper;
        this.eventLocationService = eventLocationService;
    }

    // Return all locations
    @GetMapping
    public List<EventLocationDto> getAll() {
        return eventLocationMapper.toDto(eventLocationService.getAll());
    }

    @GetMapping("/{id}")
    public EventLocationDto getById(@PathVariable(name = "id") Integer id) {
        return eventLocationMapper.toDto(eventLocationService.getById(id));
    }

    // Create new location
    @PostMapping
    public ResponseEntity<HttpStatus> create(@Valid @RequestBody EventLocationDto eventLocationDto) {
        EventLocation location = eventLocationMapper.toEntity(eventLocationDto);

        eventLocationService.save(location);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    // Update location
    public ResponseEntity<HttpStatus> update(@RequestParam(name = "id", required = true) Integer id,
                                             @Valid @RequestBody EventLocationDto eventLocationDto) {

        if (!eventLocationService.isEventLocationContains(id)){
            throw new IllegalArgumentException("You can't update event location with id = "  + id
                    + ", because is doesn't exist!");
        }

        EventLocation eventLocation = eventLocationMapper.toEntity(eventLocationDto);
        eventLocation.setId(id);

        eventLocationService.save(eventLocation);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteById(@RequestParam(name = "id", required = true) Integer id) {
        if (!eventLocationService.isEventLocationContains(id)) {
            throw new IllegalArgumentException("Event location was this id not found!");
        }
        eventLocationService.deleteById(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
