package com.cinema.app.domain.cinema_room.dto.validator;

import com.cinema.app.domain.cinema_room.dto.CreateCinemaRoomDto;
import com.cinema.app.domain.configs.validator.Validator;

import java.util.Map;

/**
 * implementation of Validator interface used to validate CreateCinemaRoomDto objects
 * @see Validator
 * @author Szymon Sawicki
 */


public class CreateCinemaRoomDtoValidator implements Validator<CreateCinemaRoomDto> {

    @Override
    public Map<String, String> validate(CreateCinemaRoomDto createCinemaRoomDto) {
        return null;
    }
}
