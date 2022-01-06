package com.app.crudapp.service.impl;

import com.app.crudapp.model.Media;
import com.app.crudapp.model.User;
import com.app.crudapp.repository.UserRepository;
import com.app.crudapp.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public User getById(int id) {
        log.debug("Get user by id: {}", id);
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getAll() {
        log.debug("Get user list");
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        user = userRepository.save(user);
        log.debug("The new user was created, id: {}", user.getId());
        return user;
    }

    @Override
    public User update(User user) {
        log.info("Starting update the user with id: {}", user.getId());
        return userRepository.save(user);
    }

    @Override
    public void deleteById(int id) {
        log.debug("Delete user by id: {}", id);
        User user = getById(id);
        userRepository.delete(user);
    }
}
