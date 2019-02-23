package com.jageton.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "messenger_sch")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String login;
    private String token;

    public User() {
    }

    public User(String login, String token) {
        this.login = login;
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return String.format(
                "Message[id=%d, login='%s', token='%s']",
                id, login, token);
    }

}
