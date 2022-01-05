package com.app.crudapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EventActions {
    CREATE("created"),
    UPDATE("updated");

    private final String action;
}
