package com.jageton.server.repositories;

import com.jageton.server.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findAll();

    List<User> findByLogin(String login);

    List<User> findByLoginOrderById(String login);

    List<User> findByToken(String token);

    List<User> findByTokenOrderById(String token);

}
