package com.app.crudapp.controller;

import com.app.crudapp.dto.UserDto;
import com.app.crudapp.model.User;
import com.app.crudapp.service.EventService;
import com.app.crudapp.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        User create = userService.save(user);
        UserDto result = UserDto.fromUser(create);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAll();
        List<UserDto> result = new ArrayList<>();
        users.forEach(user ->
                result.add(UserDto.fromUser(user)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUser(@PathVariable Integer userId) {
        User user = userService.getById(userId);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDto result = UserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteById(userId);
    }

    @GetMapping(value = "/myinfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getMyInfo(@RequestHeader HttpHeaders httpHeaders) throws JsonProcessingException {
        String token = httpHeaders.getFirst("Authorization");
        String[] chunks = token.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(chunks[1]));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);

        User user = userService.findByName(actualObj.get("sub").asText());
        UserDto userDto = UserDto.fromUser(user);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    //TODO:Update this method
    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
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
}
