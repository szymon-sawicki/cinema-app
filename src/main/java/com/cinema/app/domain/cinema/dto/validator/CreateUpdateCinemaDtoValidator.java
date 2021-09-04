package com.cinema.app.domain.cinema.dto.validator;

import com.cinema.app.domain.address.dto.validator.CreateUpdateAddressDtoValidator;
import com.cinema.app.domain.cinema.dto.CreateUpdateCinemaDto;
import com.cinema.app.domain.cinema_room.dto.validator.CreateCinemaRoomDtoValidator;
import com.cinema.app.domain.configs.validator.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * implementation of Validator interface used to validate CreateCinemaDto objects
 * @see Validator
 * @author Szymon Sawicki
 */


public class CreateUpdateCinemaDtoValidator implements Validator<CreateUpdateCinemaDto> {

    @Override
    public Map<String, String> validate(CreateUpdateCinemaDto createUpdateCinemaDto) {
        var errors = new HashMap<String, String>();

        if(createUpdateCinemaDto == null) {
            errors.put("create cinema dto","is null");
            return errors;
        }

        if(createUpdateCinemaDto.getCreateAddressDto() == null) {
            errors.put("create address dto","is null");
        } else {
            errors.putAll(new CreateUpdateAddressDtoValidator().validate(createUpdateCinemaDto.getCreateAddressDto()));
        }

        if(createUpdateCinemaDto.getCinemaRoomDtos() == null) {
            errors.put("cinema room dtos","are null");
        } else {
            var cinemaRoomDtos = createUpdateCinemaDto.getCinemaRoomDtos();

            if(cinemaRoomDtos.isEmpty()) {
                errors.put("cinema room dtos","is empty");
            } else {
                cinemaRoomDtos.forEach(cinemaRoomDto -> errors.putAll(new CreateCinemaRoomDtoValidator().validate(cinemaRoomDto)));
            }
        }

        if (createUpdateCinemaDto.getName() == null) {
            errors.put("cinema room name", "is null");
        } else if (!createUpdateCinemaDto.getName().matches("[\\w\\s\\-]{3,30}+")) {
            errors.put("cinema name", "have wrong format");
        }


        return errors;

    }
}
