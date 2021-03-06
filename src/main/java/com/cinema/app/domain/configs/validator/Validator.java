package com.cinema.app.domain.configs.validator;

import java.util.Map;
import java.util.stream.Collectors;

public interface Validator<T> {

    Map<String,String> validate(T t);

    static <T> void validate(Validator<T> validator, T t) {

        var errors = validator.validate(t);

        if(!errors.isEmpty()) {
            var message = "[VALIDATION ERRORS]: " + errors
                    .entrySet()
                    .stream()
                    .map(e->e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ValidatorException(message);
        }

    }
}
