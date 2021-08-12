package com.cinema.app.domain.cinema.dto.validator;

import com.cinema.app.domain.cinema.dto.CreateCinemaDto;
import com.cinema.app.domain.configs.validator.Validator;

import java.util.Map;

/**
 * implementation of Validator interface used to validate CreateCinemaDto objects
 * @see Validator
 * @author Szymon Sawicki
 */


public class CreateCinemaDtoValidator implements Validator<CreateCinemaDto> {

    @Override
    public Map<String, String> validate(CreateCinemaDto createCinemaDto) {
        return null;
    }
}
