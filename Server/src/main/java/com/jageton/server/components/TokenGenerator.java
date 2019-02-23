package com.jageton.server.components;

import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;

public class TokenGenerator {

    private int length;

    @Autowired
    public TokenGenerator(int length) {
        this.length = length;
    }

    public String generate() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[length];
        random.nextBytes(bytes);
        return bytes.toString();
    }
}
