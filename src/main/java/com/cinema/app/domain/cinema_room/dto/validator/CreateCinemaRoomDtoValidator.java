package com.cinema.app.domain.cinema_room.dto.validator;

import com.cinema.app.domain.cinema_room.dto.CreateCinemaRoomDto;
import com.cinema.app.domain.configs.validator.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * implementation of Validator interface used to validate CreateCinemaRoomDto objects
 *
 * @author Szymon Sawicki
 * @see Validator
 */


public class CreateCinemaRoomDtoValidator implements Validator<CreateCinemaRoomDto> {

    @Override
    public Map<String, String> validate(CreateCinemaRoomDto createCinemaRoomDto) {

        var errors = new HashMap<String, String>();

        if (createCinemaRoomDto == null) {
            errors.put("create cinema room dto", "is null");
            return errors;
        }

        if (createCinemaRoomDto.getName() == null) {
            errors.put("cinema room name", "is null");
        } else if (!createCinemaRoomDto.getName().matches("[\\w\\s\\-]{3,30}+")) {
            errors.put("cinema room name", "have wrong format");
        }

        if (createCinemaRoomDto.getRowsNum() == null) {
            errors.put("rows", "is null");
        } else if (createCinemaRoomDto.getRowsNum() <= 0) {
            errors.put("rows", "is equal or smaller than 0");
        }

        if (createCinemaRoomDto.getPlaceNumber() == null) {
            errors.put("place number", "is null");
        } else if (createCinemaRoomDto.getPlaceNumber() <= 0) {
            errors.put("place number", "is equal or smaller than 0");
        }
/*

        if (createCinemaRoomDto.getCinemaId() == null) {
            errors.put("cinema id", "is null");
        } else if (createCinemaRoomDto.getCinemaId() <= 0) {
            errors.put("cinema id", "is equal or smaller than 0");
        }
*/

        return errors;


    }
}
