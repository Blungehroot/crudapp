package com.app.crudapp.dto;

import com.app.crudapp.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Integer id;
    private String name;
    private Status status;
    private List<Role> roles;
    @JsonIgnore
    private List<Event> events;

    @JsonIgnore()
    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setStatus(user.getStatus());
        userDto.setRoles(user.getRoles());

        return userDto;
    }

    public static UserDto fromUserWithEvents(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setStatus(user.getStatus());
        userDto.setRoles(user.getRoles());
        userDto.setEvents(user.getEvents());

        return userDto;
    }

    public User toUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setStatus(userDto.getStatus());
        user.setRoles(userDto.getRoles());
        user.setEvents(userDto.getEvents());

        return user;
    }
}
