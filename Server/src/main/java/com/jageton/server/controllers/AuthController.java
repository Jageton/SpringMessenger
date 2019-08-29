package com.jageton.server.controllers;

import com.jageton.server.data.UserData;
import com.jageton.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String login() {
        return "login.html";
    }

    @PostMapping
    @ResponseBody
    public UserData getToken(@RequestBody String login) {
        return userService.requestToken(login);
    }
}


