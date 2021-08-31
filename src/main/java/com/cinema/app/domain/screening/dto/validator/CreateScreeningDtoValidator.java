package com.cinema.app.domain.screening.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.movie.dto.GetMovieDto;
import com.cinema.app.domain.screening.dto.CreateScreeningDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
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


        if(createScreeningDto.getCreateMovieDto() == null) {
            errors.put("create movie dto","is null");
        }

        if(createScreeningDto.getCinemaRoomId() == null) {
            errors.put("cinema room id","is null");
        } else if(createScreeningDto.getCinemaRoomId() <= 0) {
            errors.put("cinema id","is lower or equal to 0");
        }

        if(createScreeningDto.getDateTime() == null) {
            errors.put("screening date and time","is null");
        } else if(createScreeningDto.getDateTime().isBefore(LocalDateTime.now())) {
            errors.put("screening date","is in the past");
        }

        return errors;
    }
}
