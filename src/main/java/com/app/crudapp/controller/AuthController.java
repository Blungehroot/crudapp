package com.app.crudapp.controller;

import com.app.crudapp.dto.AuthentificationRequestDto;
import com.app.crudapp.model.User;
import com.app.crudapp.security.jwt.JwtTokenProvider;
import com.app.crudapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    public ResponseEntity login(@RequestBody AuthentificationRequestDto authentificationRequestDto) {
        try {
            String name = authentificationRequestDto.getName();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, authentificationRequestDto.getPassword()));
            User user = userService.findByName(name);

            if (user == null) {
                throw new UsernameNotFoundException("User with name: " + name + "  not found");
            }

            String token = jwtTokenProvider.createToken(name, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("name", name);
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid name or password");
        }
    }


}
