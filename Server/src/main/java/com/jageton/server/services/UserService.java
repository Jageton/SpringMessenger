package com.jageton.server.services;

import com.jageton.server.data.UserData;
import com.jageton.server.entities.User;
import com.jageton.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserData requestToken(String login) {
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
