package com.nforge.healthymorningsapi.payload;

import lombok.Getter;


public class AuthorizationResponce {
    @Getter
    private String token;
    private long expiresIn;
}
