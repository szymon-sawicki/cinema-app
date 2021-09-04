package com.cinema.app.domain.screening.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.screening.dto.CreateUpdateScreeningDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CreateUpdateScreeningDtoValidator implements Validator<CreateUpdateScreeningDto> {
    @Override
    public Map<String, String> validate(CreateUpdateScreeningDto createUpdateScreeningDto) {
        var errors = new HashMap<String,String>();

        if(createUpdateScreeningDto == null) {
            errors.put("create screening dto","is null");
            return errors;
        }


        if(createUpdateScreeningDto.getCreateUpdateMovieDto() == null) {
            errors.put("create movie dto","is null");
        }

        if(createUpdateScreeningDto.getCinemaRoomId() == null) {
            errors.put("cinema room id","is null");
        } else if(createUpdateScreeningDto.getCinemaRoomId() <= 0) {
            errors.put("cinema id","is lower or equal to 0");
        }

        if(createUpdateScreeningDto.getDateTime() == null) {
            errors.put("screening date and time","is null");
        } else if(createUpdateScreeningDto.getDateTime().isBefore(LocalDateTime.now())) {
            errors.put("screening date","is in the past");
        }

        return errors;
    }
}
