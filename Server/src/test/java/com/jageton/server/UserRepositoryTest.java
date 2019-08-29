package com.jageton.server;

import com.jageton.server.entities.User;
import com.jageton.server.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
public class UserRepositoryTest {

    @Autowired
    public UserRepository userRepository;

    @Test
    @Transactional
    public void saveUserTest() {
        User user = new User("login");
        userRepository.save(user);

        List<User> users = userRepository.findAll();
        Assert.assertFalse(users.isEmpty());

        System.out.println(user.toString());
    }
}
