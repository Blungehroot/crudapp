package com.app.crudapp.security.jwt;

import com.app.crudapp.model.UserRoles;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtTokenProvider {

    public String createToken(String name, List<UserRoles> roles) {

    }

    public Authentication getAuthentication(String token) {

    }

    public String getName(String token) {

    }

    public boolean validateToken(String token) {

    }

    private List<String> getRoles(List<UserRoles> roles) {
        List<String> result = new ArrayList<>();
        roles.forEach(role -> {
            result.add(role.getRole());
        });
        return result;
    }
}
