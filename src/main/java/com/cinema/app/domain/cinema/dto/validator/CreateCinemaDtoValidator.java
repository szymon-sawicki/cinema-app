package com.cinema.app.domain.cinema.dto.validator;

import com.cinema.app.domain.address.dto.validator.CreateAddressDtoValidator;
import com.cinema.app.domain.cinema.dto.CreateCinemaDto;
import com.cinema.app.domain.cinema_room.dto.validator.CreateCinemaRoomDtoValidator;
import com.cinema.app.domain.configs.validator.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * implementation of Validator interface used to validate CreateCinemaDto objects
 * @see Validator
 * @author Szymon Sawicki
 */


public class CreateCinemaDtoValidator implements Validator<CreateCinemaDto> {

    @Override
    public Map<String, String> validate(CreateCinemaDto createCinemaDto) {
        var errors = new HashMap<String, String>();

        if(createCinemaDto == null) {
            errors.put("create cinema dto","is null");
            return errors;
        }

        if(createCinemaDto.getCreateAddressDto() == null) {
            errors.put("create address dto","is null");
        } else {
            errors.putAll(new CreateAddressDtoValidator().validate(createCinemaDto.getCreateAddressDto()));
        }

        if(createCinemaDto.getCinemaRoomDtos() == null) {
            errors.put("cinema room dtos","are null");
        } else {
            var cinemaRoomDtos = createCinemaDto.getCinemaRoomDtos();

            if(cinemaRoomDtos.isEmpty()) {
                errors.put("cinema room dtos","is empty");
            } else {
                cinemaRoomDtos.forEach(cinemaRoomDto -> errors.putAll(new CreateCinemaRoomDtoValidator().validate(cinemaRoomDto)));
            }
        }

        return errors;

    }
}
