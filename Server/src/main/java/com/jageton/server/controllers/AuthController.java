package com.jageton.server.controllers;

import com.jageton.server.components.TokenGenerator;
import com.jageton.server.data.UserData;
import com.jageton.server.entities.User;
import com.jageton.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenGenerator tokenGenerator;

    @GetMapping
    public String login() {
        return "login.html";
    }

    @PostMapping
    @ResponseBody
    public UserData getToken(@RequestBody String login) {
        System.out.println("getToken()  has been invoked; login is " + login);
        String token;
        User user = userRepository.findByLogin(login);

        if (user == null) {
            token = generateToken();
            userRepository.save(new User(login, token));
        } else {
            token = user.getToken();
        }

        return new UserData(login, token);
    }

    private String generateToken() {
        User user;
        String token;

        // check generated token on unique:
        do {
            token = tokenGenerator.generate();
            user = userRepository.findByToken(token);
        } while (user == null);

        return token;
    }

}


