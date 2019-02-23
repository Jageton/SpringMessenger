package com.jageton.server.controllers;

import com.jageton.server.entities.Message;
import com.jageton.server.entities.User;
import com.jageton.server.repositories.MessageRepository;
import com.jageton.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/send_message")
    @SendTo("/topic/activity")
    public List<Message> sendMessage(Message message) {
        System.out.println("sendMessage()  has been invoked; message is \" + message");
        Message answer = new Message("Server", message.getFrom(),
                "The server says hello!");

        messageRepository.save(message);
        messageRepository.save(answer);

        return new LinkedList<>() {{
            add(answer);
        }};
    }

    @MessageMapping("/history")
    @SendTo("/topic/activity")
    @ResponseBody
    public List<Message> getDialogHistory(@RequestBody Map<String, String> jsonToken) {
        String token = jsonToken.get("token");
        System.out.println("getDialogHistory()  has been invoked; token is " + token);
        User user = userRepository.findByToken(token);

        if (user == null) {
            System.out.println("User not found !");
            // return/throw exception ?
        }

        String login = user.getLogin();
        return messageRepository.findByFromOrToOrderById(login, login);
    }

}
