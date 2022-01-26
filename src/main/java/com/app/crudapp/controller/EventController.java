package com.app.crudapp.controller;

import com.app.crudapp.model.Event;
import com.app.crudapp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAll();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping(value = "/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> getEventById(@PathVariable Integer eventId) {
        Event event = eventService.getById(eventId);

        if (event == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(event, HttpStatus.OK);
    }
}
