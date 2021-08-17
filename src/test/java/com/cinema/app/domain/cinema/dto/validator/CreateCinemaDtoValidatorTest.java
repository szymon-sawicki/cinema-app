package com.cinema.app.domain.cinema.dto.validator;

import com.cinema.app.domain.cinema.dto.CreateCinemaDto;
import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.configs.validator.ValidatorException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateCinemaDtoValidatorTest {

    @Test
    @DisplayName("when create cinema dto is null")
    public void test1() {

        var createCinemaDtoValidator = new CreateCinemaDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createCinemaDtoValidator, null))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS")
                .hasMessageContaining("create cinema dto: is null");
    }

    @Test
    @DisplayName("when create address dto is null")
    public void test2() {

        var createCinemaDtoValidator = new CreateCinemaDtoValidator();
        var createCinemaDto = CreateCinemaDto.builder()
                .createAddressDto(null)
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createCinemaDtoValidator, createCinemaDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS")
                .hasMessageContaining("create address dto: is null");

    }

    @Test
    @DisplayName("when cinema rooms dtos are null")
    public void test3() {

        var createCinemaDtoValidator = new CreateCinemaDtoValidator();
        var createCinemaDto = CreateCinemaDto.builder()
                .cinemaRoomDtos(null)
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createCinemaDtoValidator, createCinemaDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("cinema room dtos: are null");
    }

}
