package com.svop.service.secutity;


import org.springframework.security.core.AuthenticationException;

public class JwtAuthentificationExeption extends AuthenticationException {
    public JwtAuthentificationExeption(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthentificationExeption(String msg) {
        super(msg);
    }
}
