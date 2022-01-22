package com.app.crudapp.security.jwt;

import com.app.crudapp.model.Role;
import com.app.crudapp.model.Status;
import com.app.crudapp.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(), user.getName(), user.getPassword(), mapToGrantedAuthorities(new ArrayList<>(user.getRoles())), user.getStatus().equals(Status.ACTIVE));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        return roles.stream().map(role ->
                new SimpleGrantedAuthority(role.getName())
        ).collect(Collectors.toList());
    }

}
