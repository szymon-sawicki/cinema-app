package com.cinema.app.domain.seat.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.seat.dto.CreateUpdateSeatDto;

import java.util.HashMap;
import java.util.Map;

/**
 * implementation of Validator interface used to validate CreateSeatDto objects
 *
 * @author Szymon Sawicki
 * @see Validator
 */


public class CreateUpdateSeatDtoValidator implements Validator<CreateUpdateSeatDto> {

    @Override
    public Map<String, String> validate(CreateUpdateSeatDto createUpdateSeatDto) {
        var errors = new HashMap<String, String>();

        if (createUpdateSeatDto == null) {
            errors.put("create seat dto", "is null");
            return errors;
        }

        if (createUpdateSeatDto.getSeatType() == null) {
            errors.put("seat type", "is null");
        }

        if (createUpdateSeatDto.getPlace() == null) {
            errors.put("place number", "is null");
        } else if (createUpdateSeatDto.getPlace() <= 0) {
            errors.put("place number", "is equal or smaller than 0");
        }

        if (createUpdateSeatDto.getRowNum() == null) {
            errors.put("row number", "is null");
        } else if (createUpdateSeatDto.getRowNum() <= 0) {
            errors.put("row number", "is equal or smaller than 0");
        }

        if (createUpdateSeatDto.getCinemaRoomId() == null) {
            errors.put("cinema room id", "is null");
        } else if (createUpdateSeatDto.getCinemaRoomId() <= 0) {
            errors.put("cinema room id", "is equal or smaller than 0");
        }

        return errors;

    }
}
