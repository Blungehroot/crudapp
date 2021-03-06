package com.app.crudapp.controller;

import com.app.crudapp.dto.UserDto;
import com.app.crudapp.model.User;
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
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private User getUserFromToken(HttpHeaders httpHeaders) throws JsonProcessingException {
        String token = httpHeaders.getFirst("Authorization");
        String[] chunks = token.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(chunks[1]));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);

        return userService.findByName(actualObj.get("sub").asText());
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        return new ResponseEntity<>(UserDto.fromUser(userService.save(user)), HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> result = new ArrayList<>();
        userService.getAll().forEach(user ->
                result.add(UserDto.fromUser(user)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUser(@PathVariable Integer userId) {
        return new ResponseEntity<>(UserDto.fromUser(userService.getById(userId)), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteById(userId);
    }

    @GetMapping(value = "/my-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getMyInfo(@RequestHeader HttpHeaders httpHeaders) throws JsonProcessingException {
        User user = getUserFromToken(httpHeaders);
        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.OK);
    }

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
