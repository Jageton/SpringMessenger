package com.jageton.server.controllers;

import com.jageton.server.entities.Message;
import com.jageton.server.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("/send_message")
    @SendTo("/topic/activity")
    public List<Message> sendMessage(Message message) {
        Message answer = messageService.saveAndAnswer(message);
        List<Message> result = new ArrayList<>();
        result.add(answer);
        return result;
    }

    @MessageMapping("/history")
    @SendTo("/topic/activity")
    @ResponseBody
    public List<Message> getDialogHistory(@RequestBody Map<String, String> jsonToken) {
        return messageService.history(jsonToken);
    }

}
