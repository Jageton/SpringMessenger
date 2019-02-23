package com.jageton.server.controllers;

import com.jageton.server.components.TokenGenerator;
import com.jageton.server.data.UserData;
import com.jageton.server.entities.User;
import com.jageton.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
        List<User> list = userRepository.findByLoginOrderById(login);// redo on User.class

        if (list.size() == 0) {
            token = tokenGenerator.generate();// generate token;
            userRepository.save(new User(login, token));
        } else {
            User user = list.get(0);
            token = user.getToken();
            if (token.equals("")) {
                token = tokenGenerator.generate();// generate token;
                // check generated token on uniq;
                user.setToken(token);
                userRepository.save(user);
            }
        }

        return new UserData(login, token);
    }

}


