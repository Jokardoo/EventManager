package jokardoo.eventmanager.controller;

import jakarta.validation.Valid;
import jokardoo.eventmanager.domain.location.EventLocation;
import jokardoo.eventmanager.dto.location.EventLocationDto;
import jokardoo.eventmanager.mapper.location.EventLocationModelToDtoMapper;
import jokardoo.eventmanager.service.EventLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {
    private final EventLocationModelToDtoMapper eventLocationModelToDtoMapper;
    private final EventLocationService eventLocationService;


    @GetMapping
    public List<EventLocationDto> getAll() {
        return eventLocationModelToDtoMapper.toDto(eventLocationService.getAll());
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<EventLocationDto> getById(@PathVariable(name = "locationId") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventLocationModelToDtoMapper.toDto(eventLocationService.getById(id)));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<HttpStatus> create(@Valid @RequestBody EventLocationDto eventLocationDto) {
        EventLocation location = eventLocationModelToDtoMapper.toModel(eventLocationDto);

        eventLocationService.save(location);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> update(@RequestParam(name = "locationId") Integer id,
                                             @Valid @RequestBody EventLocationDto eventLocationDto) {

        eventLocationService.update(id, eventLocationModelToDtoMapper.toModel(eventLocationDto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteById(@RequestParam(name = "id") Integer id) {
        eventLocationService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
