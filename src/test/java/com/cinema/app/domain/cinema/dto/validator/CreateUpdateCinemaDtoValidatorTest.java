package com.cinema.app.domain.cinema.dto.validator;

import com.cinema.app.domain.address.dto.CreateUpdateAddressDto;
import com.cinema.app.domain.cinema.dto.CreateUpdateCinemaDto;
import com.cinema.app.domain.cinema_room.dto.CreateUpdateCinemaRoomDto;
import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.configs.validator.ValidatorException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class CreateUpdateCinemaDtoValidatorTest {

    @Test
    @DisplayName("when create cinema dto is null")
    public void test1() {

        var createCinemaDtoValidator = new CreateUpdateCinemaDtoValidator();

        assertThatThrownBy(() -> Validator.validate(createCinemaDtoValidator, null))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS")
                .hasMessageContaining("create cinema dto: is null");
    }

    @Test
    @DisplayName("when create address dto is null")
    public void test2() {

        var createCinemaDtoValidator = new CreateUpdateCinemaDtoValidator();
        var createCinemaDto = CreateUpdateCinemaDto.builder()
                .createAddressDto(null)
                .build();

        assertThatThrownBy(() -> Validator.validate(createCinemaDtoValidator, createCinemaDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS")
                .hasMessageContaining("create address dto: is null");

    }

    @Test
    @DisplayName("when cinema rooms dtos are null")
    public void test3() {

        var createCinemaDtoValidator = new CreateUpdateCinemaDtoValidator();
        var createCinemaDto = CreateUpdateCinemaDto.builder()
                .cinemaRoomDtos(null)
                .build();

        assertThatThrownBy(() -> Validator.validate(createCinemaDtoValidator, createCinemaDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("cinema room dtos: are null");
    }

    @Test
    @DisplayName("when name is incorrect")
    public void test4() {

        var createCinemaDtoValidator = new CreateUpdateCinemaDtoValidator();
        var createCinemaDto = CreateUpdateCinemaDto.builder()
                .name("Klo&%7")
                .build();

        assertThatThrownBy(() -> Validator.validate(createCinemaDtoValidator, createCinemaDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("cinema name: have wrong format");
    }

    @Test
    @DisplayName("when create cinema dto is correct")
    public void test5() {


        var createCinemaDtoValidator = new CreateUpdateCinemaDtoValidator();

        var createAddressDto = CreateUpdateAddressDto.builder()
                .zipCode("956-95")
                .city("Prague")
                .street("Main Street")
                .houseNumber("234/5")
                .build();

        var createCinemaRoomDto = CreateUpdateCinemaRoomDto.builder()
                .name("magic room")
                .rowsNum(30)
                .placeNumber(20)
                .cinemaId(3L)
                .build();

        var createCinemaDto = CreateUpdateCinemaDto.builder()
                .name("Nice Kino")
                .createAddressDto(createAddressDto)
                .cinemaRoomDtos(List.of(createCinemaRoomDto))
                .build();

        assertDoesNotThrow(() -> Validator.validate(createCinemaDtoValidator,createCinemaDto));

    }

}
