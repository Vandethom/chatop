package com.chatop.api.controller;

public class AuthSuccess {
    private String token;

    public AuthSuccess(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}