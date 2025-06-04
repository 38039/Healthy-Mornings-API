package com.nforge.healthymorningsapi.payload;


public class AuthorizationResponce {
    private String token;
    private long expiresIn;

    public AuthorizationResponce setToken(String token) {
        this.token = token;
        return this;
    }

    public AuthorizationResponce setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }
}
