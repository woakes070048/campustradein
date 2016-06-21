package com.cti.auth;

import java.time.LocalDateTime;

/**
 * @author ifeify
 */
public class Token {
    private final static long EXPIRATION_TIME = 60 * 24;
    private String token;
    private String username;
    private LocalDateTime expirationDateTime;

    public Token() {}

    public Token(String username) {
        this.username = username;
        expirationDateTime = LocalDateTime.now().plusMinutes(EXPIRATION_TIME);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(LocalDateTime expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public boolean hasExpired() {
        return expirationDateTime.isBefore(LocalDateTime.now());
    }
}
