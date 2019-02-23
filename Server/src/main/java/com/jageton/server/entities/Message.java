package com.jageton.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "dialog", schema = "messenger_sch")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "sender")
    private String from;
    @Column(name = "recipient")
    private String to;
    private String message;

    public Message() {
    }

    public Message(String from, String to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format(
                "Message[id=%d, from='%s', to='%s', message='%s']",
                id, from, to, message);
    }

}
