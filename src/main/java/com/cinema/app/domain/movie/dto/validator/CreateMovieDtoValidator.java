package com.cinema.app.domain.movie.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.movie.dto.CreateMovieDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CreateMovieDtoValidator implements Validator<CreateMovieDto> {

    @Override
    public Map<String, String> validate(CreateMovieDto createMovieDto) {
        var errors = new HashMap<String, String>();

        if (createMovieDto == null) {
            errors.put("create movie dto", "is null");
            return errors;
        }

        if (createMovieDto.getName() == null) {
            errors.put("name", "is null");
        } else if (!createMovieDto.getName().matches("[\\w\\s\\-'.,]{2,50}+")) {
            errors.put("name", "have wrong format");
        }

        if (createMovieDto.getMovieGenre() == null) {
            errors.put("movie genre", "is null");
        }

        if (createMovieDto.getPremiereDate() == null) {
            errors.put("premiere date", "is null");
        } else if (createMovieDto.getPremiereDate().isAfter(LocalDate.now())) {
            errors.put("premiere date", "is in the future");
        }
        if (createMovieDto.getLength() == null) {
            errors.put("length","is null");
        } else if (createMovieDto.getLength() <= 0) {
            errors.put("length", "is equal or smaller than 0");
        } else if (createMovieDto.getLength() > 240) {
            errors.put("length", "is too big");
        }

        return errors;
    }
}
