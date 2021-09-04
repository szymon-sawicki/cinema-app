package com.cinema.app.domain.cinema_room.dto.validator;

import com.cinema.app.domain.cinema_room.dto.CreateUpdateCinemaRoomDto;
import com.cinema.app.domain.configs.validator.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * implementation of Validator interface used to validate CreateCinemaRoomDto objects
 *
 * @author Szymon Sawicki
 * @see Validator
 */


public class CreateCinemaRoomDtoValidator implements Validator<CreateUpdateCinemaRoomDto> {

    @Override
    public Map<String, String> validate(CreateUpdateCinemaRoomDto createUpdateCinemaRoomDto) {

        var errors = new HashMap<String, String>();

        if (createUpdateCinemaRoomDto == null) {
            errors.put("create cinema room dto", "is null");
            return errors;
        }

        if (createUpdateCinemaRoomDto.getName() == null) {
            errors.put("cinema room name", "is null");
        } else if (!createUpdateCinemaRoomDto.getName().matches("[\\w\\s\\-]{3,30}+")) {
            errors.put("cinema room name", "have wrong format");
        }

        if (createUpdateCinemaRoomDto.getRowsNum() == null) {
            errors.put("rows", "is null");
        } else if (createUpdateCinemaRoomDto.getRowsNum() <= 0) {
            errors.put("rows", "is equal or smaller than 0");
        }

        if (createUpdateCinemaRoomDto.getPlaceNumber() == null) {
            errors.put("place number", "is null");
        } else if (createUpdateCinemaRoomDto.getPlaceNumber() <= 0) {
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
