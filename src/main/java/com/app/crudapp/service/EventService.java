package com.app.crudapp.service;

import com.app.crudapp.model.Event;

import java.util.List;

public interface EventService {
    Event getById(int id);

    List<Event> getAll();

    Event save(Event event);

    Event update(Event event);

    void deleteById(int id);
}
