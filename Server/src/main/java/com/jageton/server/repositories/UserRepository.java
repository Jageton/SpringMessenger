package com.jageton.server.repositories;

import com.jageton.server.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {

    List<User> findAll();

    User findByLogin(String login);

    User findByToken(String token);

}
