package com.cinema.app.domain.movie.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.configs.validator.ValidatorException;
import com.cinema.app.domain.movie.dto.CreateUpdateMovieDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CreateUpdateMovieDtoValidatorTest {

    @Test
    @DisplayName("when create movie dto is null")
    public void test1() {

        var createMovieDtoValidator = new CreateUpdateMovieDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createMovieDtoValidator,null))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("create movie dto: is null");
    }

    @Test
    @DisplayName("when movie name have wrong format")
    public void test2() {

        var createMovieDtoValidator = new CreateUpdateMovieDtoValidator();

        var createMovieDto = CreateUpdateMovieDto.builder()
                .title("alien#")
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createMovieDtoValidator,createMovieDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("title: have wrong format");
    }

    @Test
    @DisplayName("when movie genre is null")
    public void test3() {

        var createMovieDtoValidator = new CreateUpdateMovieDtoValidator();

        var createMovieDto = CreateUpdateMovieDto.builder()
                .movieGenre(null)
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createMovieDtoValidator,createMovieDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("movie genre: is null");
    }

    @Test
    @DisplayName("when premiere date is in the future")
    public void test4() {

        var createMovieDtoValidator = new CreateUpdateMovieDtoValidator();

        var futureDate = LocalDate.now().plusMonths(5);

        var createMovieDto = CreateUpdateMovieDto.builder()
                .premiereDate(futureDate)
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createMovieDtoValidator,createMovieDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("premiere date: is in the future");
    }

    @Test
    @DisplayName("when movie is too length")
    public void test5() {

        var createMovieDtoValidator = new CreateUpdateMovieDtoValidator();

        var createMovieDto = CreateUpdateMovieDto.builder()
                .length(500)
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createMovieDtoValidator,createMovieDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("length: is too big");
    }

    @Test
    @DisplayName("when movie length is negative")
    public void test6() {

        var createMovieDtoValidator = new CreateUpdateMovieDtoValidator();

        var createMovieDto = CreateUpdateMovieDto.builder()
                .length(-40)
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createMovieDtoValidator,createMovieDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("length: is equal or smaller than 0");
    }

    @Test
    @DisplayName("when premiere date is null")
    public void test7() {

        var createMovieDtoValidator = new CreateUpdateMovieDtoValidator();

        var createMovieDto = CreateUpdateMovieDto.builder()
                .premiereDate(null)
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createMovieDtoValidator,createMovieDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("premiere date: is null");
    }



}
