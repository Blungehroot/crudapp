package com.app.crudapp.dto;

import com.app.crudapp.model.Status;
import com.app.crudapp.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Integer id;
    private String name;
    private Status status;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setStatus(status);

        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setStatus(user.getStatus());

        return userDto;
    }
}
