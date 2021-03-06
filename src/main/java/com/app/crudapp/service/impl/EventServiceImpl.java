package com.app.crudapp.service.impl;

import com.app.crudapp.model.Event;
import com.app.crudapp.repository.EventRepository;
import com.app.crudapp.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventServiceImpl implements EventService {
    @Autowired
    private final EventRepository eventRepository;

    @Override
    public Event getById(int id) {
        log.debug("Get event by id: {}", id);
        return eventRepository.findById(id).get();
    }

    @Override
    public List<Event> getAll() {
        log.debug("Get events lists");
        return eventRepository.findAll();
    }

    @Override
    public Event save(Event event) {
        event = eventRepository.save(event);
        log.debug("The new event was created, id: {}", event.getId());
        return event;
    }
}
