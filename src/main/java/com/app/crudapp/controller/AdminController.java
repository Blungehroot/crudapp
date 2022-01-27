package com.app.crudapp.controller;

import com.app.crudapp.dto.UserDto;
import com.app.crudapp.model.User;
import com.app.crudapp.service.EventService;
import com.app.crudapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminController {
    private UserService userService;
    private EventService eventService;

    @Autowired
    public AdminController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        User create = userService.save(user);
        UserDto result = UserDto.fromUser(create);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAll();
        List<UserDto> result = new ArrayList<>();
        users.forEach(user ->
                result.add(UserDto.fromUser(user)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUser(@PathVariable Integer userId) {
        User user = userService.getById(userId);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDto result = UserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer userId, @RequestBody User user) {
        User current = userService.getById(userId);
        if (current == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(userId);
        UserDto userDto = UserDto.fromUser(userService.update(user));

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteById(userId);
    }
}
