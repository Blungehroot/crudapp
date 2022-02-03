package com.app.crudapp.controller;

import com.app.crudapp.dto.EventDto;
import com.app.crudapp.model.Event;
import com.app.crudapp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<Event> events = eventService.getAll();
        List<EventDto> result = new ArrayList<>();
        events.forEach(event -> result.add(EventDto.fromEvent(event)));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @GetMapping(value = "/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDto> getEventById(@PathVariable Integer eventId) {
        Event event = eventService.getById(eventId);

        if (event == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        EventDto eventDto = EventDto.fromEvent(event);

        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }
}
