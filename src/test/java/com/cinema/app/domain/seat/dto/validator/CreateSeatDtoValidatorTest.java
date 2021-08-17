package com.cinema.app.domain.seat.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.configs.validator.ValidatorException;
import com.cinema.app.domain.seat.dto.CreateSeatDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateSeatDtoValidatorTest {

    @Test
    @DisplayName("when create seat dto is null")
    public void test1() {

        var createSeatDtoValidator = new CreateSeatDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createSeatDtoValidator, null))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("create seat dto: is null");
    }

    @Test
    @DisplayName("when seat type is null")
    public void test2() {

        var createSeatDtoValidator = new CreateSeatDtoValidator();
        var createSeatDto = CreateSeatDto.builder()
                .seatType(null)
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createSeatDtoValidator, createSeatDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("seat type: is null");
    }

    @Test
    @DisplayName("when place number is negative")
    public void test3() {

        var createSeatDtoValidator = new CreateSeatDtoValidator();
        var createSeatDto = CreateSeatDto.builder()
                .place(-3)
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createSeatDtoValidator, createSeatDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("place number: is equal or smaller than 0");
    }

    @Test
    @DisplayName("when place number is 0")
    public void test4() {

        var createSeatDtoValidator = new CreateSeatDtoValidator();
        var createSeatDto = CreateSeatDto.builder()
                .place(0)
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createSeatDtoValidator, createSeatDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("place number: is equal or smaller than 0");
    }

    @Test
    @DisplayName("when row number is negative")
    public void test5() {
        var createSeatDtoValidator = new CreateSeatDtoValidator();
        var createSeatDto = CreateSeatDto.builder()
                .rowNum(-6)
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createSeatDtoValidator, createSeatDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("row number: is equal or smaller than 0");
    }

    @Test
    @DisplayName("when cinema room id is null")
    public void test6() {
        var createSeatDtoValidator = new CreateSeatDtoValidator();
        var createSeatDto = CreateSeatDto.builder()
                .cinemaRoomId(null)
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createSeatDtoValidator, createSeatDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("cinema room id: is null");
    }


}
