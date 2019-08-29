package com.jageton.server.services;

import com.jageton.server.entities.Message;
import com.jageton.server.entities.User;
import com.jageton.server.repositories.MessageRepository;
import com.jageton.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class MessageService {
    private MessageRepository messageRepository;
    private UserRepository userRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public Message saveAndAnswer(Message message) {
        Message answer = new Message("Server", message.getFrom(),
                "Hello!");

        messageRepository.save(message);
        return messageRepository.save(answer);
    }

    public List<Message> history(Map<String, String> jsonToken) {
        User user = userRepository.findByToken(jsonToken.get("token"));
        if (user == null) {
            return new ArrayList<>();
        }

        String login = user.getLogin();
        return messageRepository.findByFromOrToOrderById(login, login);
    }
}
