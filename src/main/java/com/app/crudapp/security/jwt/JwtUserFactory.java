package com.app.crudapp.security.jwt;

import com.app.crudapp.model.User;
import com.app.crudapp.model.UserRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(), user.getName(), user.getRole(), user.getPassword(), null);
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<UserRoles> roles) {
        return roles.stream().map(role ->
                new SimpleGrantedAuthority(role.getRole())
        ).collect(Collectors.toList());
    }

}
