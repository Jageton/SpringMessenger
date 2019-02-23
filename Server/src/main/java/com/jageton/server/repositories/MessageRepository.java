package com.jageton.server.repositories;

import com.jageton.server.entities.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Integer> {

    List<Message> findAll();

    List<Message> findByFrom(String from);

    List<Message> findByTo(String to);

    List<Message> findByFromOrToOrderById(String from, String to);

}
