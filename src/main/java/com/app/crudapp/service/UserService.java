package com.app.crudapp.service;

import com.app.crudapp.model.User;

import java.util.List;

public interface UserService {
    User getById(int id);

    User findByName(String name);

    List<User> getAll();

    User save(User user);

    User update(User user);

    void deleteById(int id);
}
