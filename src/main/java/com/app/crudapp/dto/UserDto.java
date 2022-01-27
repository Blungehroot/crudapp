package com.app.crudapp.dto;

import com.app.crudapp.model.Role;
import com.app.crudapp.model.Status;
import com.app.crudapp.model.User;
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

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setStatus(user.getStatus());
        userDto.setRoles(user.getRoles());

        return userDto;
    }
}
