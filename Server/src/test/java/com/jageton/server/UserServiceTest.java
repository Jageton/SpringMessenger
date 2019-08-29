package com.jageton.server;

import com.jageton.server.data.UserData;
import com.jageton.server.entities.User;
import com.jageton.server.repositories.UserRepository;
import com.jageton.server.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Transactional
    @Before
    public void setUp() {
        User user = new User("login");
        userRepository.save(user);
    }

    @Transactional
    @Test
    public void requestTokenTest() {
        User u1 = userRepository.findByLogin("login");
        Assert.assertTrue(u1 != null);
        String token1 = u1.getToken();
        Assert.assertTrue(token1 != null);
        UserData userData1 = userService.requestToken("login");
        Assert.assertTrue(token1.equals(userData1.getToken()));

        UserData userData2 = userService.requestToken("login1");
        User u2 = userRepository.findByLogin("login1");
        Assert.assertTrue(u2 != null);
        Assert.assertTrue(userData2.getToken().equals(u2.getToken()));
    }

}
