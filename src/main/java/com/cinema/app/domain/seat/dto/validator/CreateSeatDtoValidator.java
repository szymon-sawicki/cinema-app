package com.cinema.app.domain.seat.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.seat.dto.CreateSeatDto;

import java.util.HashMap;
import java.util.Map;

/**
 * implementation of Validator interface used to validate CreateSeatDto objects
 *
 * @author Szymon Sawicki
 * @see Validator
 */


public class CreateSeatDtoValidator implements Validator<CreateSeatDto> {

    @Override
    public Map<String, String> validate(CreateSeatDto createSeatDto) {
        var errors = new HashMap<String, String>();

        if (createSeatDto == null) {
            errors.put("create seat dto", "is null");
        }

        if (createSeatDto.getSeatType() == null) {
            errors.put("seat type", "is null");
        }

        if (createSeatDto.getPlace() == null) {
            errors.put("place number", "is null");
        } else if (createSeatDto.getPlace() <= 0) {
            errors.put("place number", "is equal or smaller than 0");
        }

        if (createSeatDto.getRowNum() == null) {
            errors.put("row number", "is null");
        } else if (createSeatDto.getRowNum() <= 0) {
            errors.put("row number", "is equal or smaller than 0");
        }

        if (createSeatDto.getCinemaRoomId() == null) {
            errors.put("cinema room id", "is null");
        } else if (createSeatDto.getCinemaRoomId() <= 0) {
            errors.put("cinema room id", "is equal or smaller than 0");
        }

        return errors;

    }
}
