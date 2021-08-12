package com.cinema.app.domain.seat.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.seat.dto.CreateSeatDto;

import java.util.Map;

/**
 * implementation of Validator interface used to validate CreateSeatDto objects
 * @see Validator
 * @author Szymon Sawicki
 */


public class CreateSeatDtoValidator implements Validator<CreateSeatDto> {

    @Override
    public Map<String, String> validate(CreateSeatDto createSeatDto) {
        return null;
    }

}
