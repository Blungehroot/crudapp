package com.app.crudapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EventActions {
    CREATE("created"),
    DELETE("deleted");

    private final String action;
}
