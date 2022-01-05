package com.app.crudapp.utils.validation;


import com.app.crudapp.model.User;
import com.app.crudapp.model.UserRoles;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserValidation {

    public User setUserRole(User user) {
        user.setRole(UserRoles.USER);
        return user;
    }
}
