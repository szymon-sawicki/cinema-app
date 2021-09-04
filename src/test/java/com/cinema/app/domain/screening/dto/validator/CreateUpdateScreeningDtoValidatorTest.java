package com.cinema.app.domain.screening.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.configs.validator.ValidatorException;
import com.cinema.app.domain.movie.dto.CreateUpdateMovieDto;
import com.cinema.app.domain.screening.dto.CreateUpdateScreeningDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CreateUpdateScreeningDtoValidatorTest {

    @Test
    @DisplayName("when create screening dto is null")
    public void test1() {

        var createScreeningDtoValidator = new CreateUpdateScreeningDtoValidator();

        assertThatThrownBy(() -> Validator.validate(createScreeningDtoValidator, null))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("create screening dto: is null");
    }

    @Test
    @DisplayName("when get movie dto is null")
    public void test2() {

        var createScreeningDtoValidator = new CreateUpdateScreeningDtoValidator();
        var createScreeningDto = CreateUpdateScreeningDto.builder()
                .createUpdateMovieDto(null)
                .build();

        assertThatThrownBy(() -> Validator.validate(createScreeningDtoValidator, createScreeningDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("create movie dto: is null");
    }

    @Test
    @DisplayName("when cinema id is 0")
    public void test3() {
        var createScreeningDtoValidator = new CreateUpdateScreeningDtoValidator();
        var createScreeningDto = CreateUpdateScreeningDto.builder()
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
        var createScreeningDtoValidator = new CreateUpdateScreeningDtoValidator();
        var createScreeningDto = CreateUpdateScreeningDto.builder()
                .cinemaRoomId(0L)
                .build();

        assertThatThrownBy(() -> Validator.validate(createScreeningDtoValidator, createScreeningDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("cinema id: is lower or equal to 0");
    }

    @Test
    @DisplayName("when date and time is null")
    public void test5() {
        var createScreeningDtoValidator = new CreateUpdateScreeningDtoValidator();
        var createScreeningDto = CreateUpdateScreeningDto.builder()
                .dateTime(null)
                .build();

        assertThatThrownBy(() -> Validator.validate(createScreeningDtoValidator, createScreeningDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("screening date and time: is null");
    }

    @Test
    @DisplayName("when date is in the past")
    public void test6() {
        var createScreeningDtoValidator = new CreateUpdateScreeningDtoValidator();
        var createScreeningDto = CreateUpdateScreeningDto.builder()
                .dateTime(LocalDateTime.now().minusYears(2))
                .build();

        assertThatThrownBy(() -> Validator.validate(createScreeningDtoValidator, createScreeningDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("screening date: is in the past");
    }


    @Test
    @DisplayName("when validation is correct")
    public void test8() {
        var createScreeningDtoValidator = new CreateUpdateScreeningDtoValidator();
        var createScreeningDto = CreateUpdateScreeningDto.builder()
                .dateTime(LocalDateTime.now().plusMonths(3))
                .createUpdateMovieDto(CreateUpdateMovieDto.builder().build())
                .cinemaRoomId(3L)
                .build();

        Assertions.assertDoesNotThrow(() -> Validator.validate(createScreeningDtoValidator,createScreeningDto));
    }











}


