package com.jageton.server.controllers;

import com.jageton.server.data.UserData;
import com.jageton.server.entities.User;
import com.jageton.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String login() {
        return "login.html";
    }

    @PostMapping
    @ResponseBody
    public UserData getToken(@RequestBody String login) {
        return Optional.ofNullable(userRepository.findByLogin(login)).map(user -> {
            String token = user.getToken();
            return new UserData(login, token);
        }).orElseGet(() -> {
            User user = new User(login);
            user = userRepository.save(user);
            return new UserData(login, user.getToken());
        });
    }
}


