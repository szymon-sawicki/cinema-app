package com.cinema.app.domain.screening.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.movie.dto.GetMovieDto;
import com.cinema.app.domain.screening.dto.CreateScreeningDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CreateScreeningDtoValidator implements Validator<CreateScreeningDto> {
    @Override
    public Map<String, String> validate(CreateScreeningDto createScreeningDto) {
        var errors = new HashMap<String,String>();

        if(createScreeningDto == null) {
            errors.put("create screening dto","is null");
            return errors;
        }


        if(createScreeningDto.getGetMovieDto() == null) {
            errors.put("get movie dto","is null");
        }

        if(createScreeningDto.getCinemaRoomId() == null) {
            errors.put("cinema room id","is null");
        } else if(createScreeningDto.getCinemaRoomId() <= 0) {
            errors.put("cinema id","is lower or equal to 0");
        }

        if(createScreeningDto.getDate() == null) {
            errors.put("screening date","is null");
        } else if(createScreeningDto.getDate().isBefore(LocalDate.now())) {
            errors.put("screening date","is in the past");
        }

        if(createScreeningDto.getTime() == null) {
            errors.put("screening time","is null");
        }

        return errors;
    }
}
