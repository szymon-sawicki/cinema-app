package com.cinema.app.domain.screening.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.configs.validator.ValidatorException;
import com.cinema.app.domain.movie.dto.GetMovieDto;
import com.cinema.app.domain.screening.dto.CreateScreeningDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CreateScreeningDtoValidatorTest {

    @Test
    @DisplayName("when create screening dto is null")
    public void test1() {

        var createScreeningDtoValidator = new CreateScreeningDtoValidator();

        assertThatThrownBy(() -> Validator.validate(createScreeningDtoValidator, null))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("create screening dto: is null");
    }

    @Test
    @DisplayName("when get movie dto is null")
    public void test2() {

        var createScreeningDtoValidator = new CreateScreeningDtoValidator();
        var createScreeningDto = CreateScreeningDto.builder()
                .getMovieDto(null)
                .build();

        assertThatThrownBy(() -> Validator.validate(createScreeningDtoValidator, createScreeningDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("get movie dto: is null");
    }

    @Test
    @DisplayName("when cinema id is 0")
    public void test3() {
        var createScreeningDtoValidator = new CreateScreeningDtoValidator();
        var createScreeningDto = CreateScreeningDto.builder()
                .cinemaRoomId(0L)
                .build();

        assertThatThrownBy(() -> Validator.validate(createScreeningDtoValidator, createScreeningDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("cinema id: is lower or equal to 0");
    }

    @Test
    @DisplayName("when cinema id is negative")
    public void test4() {
        var createScreeningDtoValidator = new CreateScreeningDtoValidator();
        var createScreeningDto = CreateScreeningDto.builder()
                .cinemaRoomId(0L)
                .build();

        assertThatThrownBy(() -> Validator.validate(createScreeningDtoValidator, createScreeningDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("cinema id: is lower or equal to 0");
    }

    @Test
    @DisplayName("when date is null")
    public void test5() {
        var createScreeningDtoValidator = new CreateScreeningDtoValidator();
        var createScreeningDto = CreateScreeningDto.builder()
                .date(null)
                .build();

        assertThatThrownBy(() -> Validator.validate(createScreeningDtoValidator, createScreeningDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("screening date: is null");
    }

    @Test
    @DisplayName("when date is in the past")
    public void test6() {
        var createScreeningDtoValidator = new CreateScreeningDtoValidator();
        var createScreeningDto = CreateScreeningDto.builder()
                .date(LocalDate.now().minusYears(2))
                .build();

        assertThatThrownBy(() -> Validator.validate(createScreeningDtoValidator, createScreeningDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("screening date: is in the past");
    }

    @Test
    @DisplayName("when time is null")
    public void test7() {
        var createScreeningDtoValidator = new CreateScreeningDtoValidator();
        var createScreeningDto = CreateScreeningDto.builder()
                .time(null)
                .build();

        assertThatThrownBy(() -> Validator.validate(createScreeningDtoValidator, createScreeningDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("screening time: is null");
    }

    @Test
    @DisplayName("when validation is correct")
    public void test8() {
        var createScreeningDtoValidator = new CreateScreeningDtoValidator();
        var createScreeningDto = CreateScreeningDto.builder()
                .time(LocalTime.now())
                .date(LocalDate.now().plusMonths(3))
                .getMovieDto(GetMovieDto.builder().build())
                .cinemaRoomId(3L)
                .build();

        Assertions.assertDoesNotThrow(() -> Validator.validate(createScreeningDtoValidator,createScreeningDto));
    }











}


