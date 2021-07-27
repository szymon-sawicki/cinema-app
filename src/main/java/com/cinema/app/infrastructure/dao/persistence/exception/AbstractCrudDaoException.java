package com.cinema.app.infrastructure.dao.persistence.exception;

public class AbstractCrudDaoException extends RuntimeException {
    public AbstractCrudDaoException(String message) {
        super(message);
    }
}
