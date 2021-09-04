package com.cinema.app.domain.movie.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.movie.dto.CreateUpdateMovieDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CreateUpdateMovieDtoValidator implements Validator<CreateUpdateMovieDto> {

    @Override
    public Map<String, String> validate(CreateUpdateMovieDto createUpdateMovieDto) {
        var errors = new HashMap<String, String>();

        if (createUpdateMovieDto == null) {
            errors.put("create movie dto", "is null");
            return errors;
        }

        if (createUpdateMovieDto.getTitle() == null) {
            errors.put("title", "is null");
        } else if (!createUpdateMovieDto.getTitle().matches("[\\w\\s\\-'.,]{2,50}+")) {
            errors.put("title", "have wrong format");
        }

        if (createUpdateMovieDto.getMovieGenre() == null) {
            errors.put("movie genre", "is null");
        }

        if (createUpdateMovieDto.getPremiereDate() == null) {
            errors.put("premiere date", "is null");
        } else if (createUpdateMovieDto.getPremiereDate().isAfter(LocalDate.now())) {
            errors.put("premiere date", "is in the future");
        }
        if (createUpdateMovieDto.getLength() == null) {
            errors.put("length","is null");
        } else if (createUpdateMovieDto.getLength() <= 0) {
            errors.put("length", "is equal or smaller than 0");
        } else if (createUpdateMovieDto.getLength() > 240) {
            errors.put("length", "is too big");
        }

        return errors;
    }
}
