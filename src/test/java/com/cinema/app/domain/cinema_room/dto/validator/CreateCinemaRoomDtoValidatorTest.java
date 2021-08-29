package com.cinema.app.domain.cinema_room.dto.validator;

import com.cinema.app.domain.cinema_room.dto.CreateCinemaRoomDto;
import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.configs.validator.ValidatorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CreateCinemaRoomDtoValidatorTest {

    @Test
    @DisplayName("when create cinema room dto is null")
    public void test1() {

        var createCinemaRoomDtoValidator = new CreateCinemaRoomDtoValidator();

        assertThatThrownBy(() -> Validator.validate(createCinemaRoomDtoValidator,null))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]: ")
                .hasMessageContaining("create cinema room dto: is null");

    }

    @Test
    @DisplayName("when cinema room name is null")
    public void test2() {

        var createCinemaRoomDtoValidator = new CreateCinemaRoomDtoValidator();

        var createCinemaRoomDto = CreateCinemaRoomDto.builder()
                .name(null)
                .build();

        assertThatThrownBy(() -> Validator.validate(createCinemaRoomDtoValidator,createCinemaRoomDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("cinema room name", "is null");

    }

    @Test
    @DisplayName("when cinema room name have wrong format")
    public void test3() {

        var createCinemaRoomDtoValidator = new CreateCinemaRoomDtoValidator();

        var createCinemaRoomDto = CreateCinemaRoomDto.builder()
                .name("kie%^ omafa")
                .build();

        assertThatThrownBy(() -> Validator.validate(createCinemaRoomDtoValidator,createCinemaRoomDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("cinema room name", "have wrong format");

    }

    @Test
    @DisplayName("when rows number is negative")
    public void test4() {

        var createCinemaRoomDtoValidator = new CreateCinemaRoomDtoValidator();

        var createCinemaRoomDto = CreateCinemaRoomDto.builder()
                .rowsNum(-5)
                .build();

        assertThatThrownBy(() -> Validator.validate(createCinemaRoomDtoValidator,createCinemaRoomDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("rows", "is equal or smaller than 0");

    }

    @Test
    @DisplayName("when place number is negative")
    public void test5() {

        var createCinemaRoomDtoValidator = new CreateCinemaRoomDtoValidator();

        var createCinemaRoomDto = CreateCinemaRoomDto.builder()
                .placeNumber(-5)
                .build();

        assertThatThrownBy(() -> Validator.validate(createCinemaRoomDtoValidator,createCinemaRoomDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("place number", "is equal or smaller than 0");

    }

/*    @Test
    @DisplayName("when cinema id is negative")
    public void test6() {

        var createCinemaRoomDtoValidator = new CreateCinemaRoomDtoValidator();

        var createCinemaRoomDto = CreateCinemaRoomDto.builder()
                .cinemaId(-2L)
                .build();

        assertThatThrownBy(() -> Validator.validate(createCinemaRoomDtoValidator,createCinemaRoomDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("cinema id", "is equal or smaller than 0");

    }

    @Test
    @DisplayName("when cinema id is null")
    public void test7() {

        var createCinemaRoomDtoValidator = new CreateCinemaRoomDtoValidator();

        var createCinemaRoomDto = CreateCinemaRoomDto.builder()
                .cinemaId(null)
                .build();

        assertThatThrownBy(() -> Validator.validate(createCinemaRoomDtoValidator,createCinemaRoomDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("cinema id", "have wrong format");
    }*/

    @Test
    @DisplayName("when cinema room is correct")
    public void test8() {

        var createCinemaRoomDtoValidator = new CreateCinemaRoomDtoValidator();

        var createCinemaRoomDto = CreateCinemaRoomDto.builder()
                .name("magic room")
                .rowsNum(30)
                .placeNumber(20)
                .cinemaId(3L)
                .build();

        Assertions.assertDoesNotThrow(() -> Validator.validate(createCinemaRoomDtoValidator,createCinemaRoomDto));
    }


}
