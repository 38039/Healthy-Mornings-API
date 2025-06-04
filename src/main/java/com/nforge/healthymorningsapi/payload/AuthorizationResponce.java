package com.nforge.healthymorningsapi.payload;


public class AuthorizationResponce {
    private String  token;
    public  String  getToken()      { return token; }

    private long    expiresIn;
    public  long    getExpiresIn()  { return expiresIn; }


    public AuthorizationResponce setToken(String token) {
        this.token = token;
        return this;
    }

    public AuthorizationResponce setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    @Override
    public String toString() {
        return
                "LoginResponse{"
                        + "token='"       + token     + '\''
                        + ", expiresIn="  + expiresIn +
                '}';
    }
}
