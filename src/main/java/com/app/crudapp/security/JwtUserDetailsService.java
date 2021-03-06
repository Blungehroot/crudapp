package com.app.crudapp.security;

import com.app.crudapp.model.User;
import com.app.crudapp.security.jwt.JwtUser;
import com.app.crudapp.security.jwt.JwtUserFactory;
import com.app.crudapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    @Lazy
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userService.findByName(name);

        if (user == null) {
            throw new UsernameNotFoundException("User with id: " + name + "not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);

        log.info("In user loadUserByUsername - user with name: {} successfully loaded", name);

        return jwtUser;
    }
}
