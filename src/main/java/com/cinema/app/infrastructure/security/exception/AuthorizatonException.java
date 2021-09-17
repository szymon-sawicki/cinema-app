package com.cinema.app.infrastructure.security.exception;

public class AuthorizatonException extends RuntimeException{
    public AuthorizatonException(String message) {
        super(message);
    }
}
