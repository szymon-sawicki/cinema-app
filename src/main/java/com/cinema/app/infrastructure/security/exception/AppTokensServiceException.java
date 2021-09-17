package com.cinema.app.infrastructure.security.exception;

public class AppTokensServiceException extends RuntimeException {
    public AppTokensServiceException(String message) {
        super(message);
    }
}
