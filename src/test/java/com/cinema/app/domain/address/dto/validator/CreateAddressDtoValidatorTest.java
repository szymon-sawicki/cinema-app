package com.cinema.app.domain.address.dto.validator;

import com.cinema.app.domain.address.dto.CreateAddressDto;
import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.configs.validator.ValidatorException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateAddressDtoValidatorTest {

    @Test
    @DisplayName("when create address dto is null")
    public void test1() {
        var createAddressDtoValidator = new CreateAddressDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createAddressDtoValidator,null))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]: ")
                .hasMessageContaining("create address dto: is null");
    }

    @Test
    @DisplayName("when street have wrong format")
    public void test2() {
        var createAddressDtoValidator = new CreateAddressDtoValidator();
        var createAddressDto = CreateAddressDto.builder()
                .street("][dddfvfd]")
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createAddressDtoValidator,createAddressDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]: ")
                .hasMessageContaining("street: have wrong format");
    }

    @Test
    @DisplayName("when street have good format")
    public void test3() {
        var createAddressDtoValidator = new CreateAddressDtoValidator();
        var createAddressDto = CreateAddressDto.builder()
                .street("Słoneczna 26a")
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createAddressDtoValidator,createAddressDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageNotContaining("street: ");
    }

    @Test
    @DisplayName("when house number contains illegal characters")
    public void test4() {
        var createAddressDtoValidator = new CreateAddressDtoValidator();
        var createAddressDto = CreateAddressDto.builder()
                .houseNumber("23*&^%")
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createAddressDtoValidator,createAddressDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]: ")
                .hasMessageContaining("house number: have wrong format");
    }

    @Test
    @DisplayName("when city is too short")
    public void test5() {
        var createAddressDtoValidator = new CreateAddressDtoValidator();
        var createAddressDto = CreateAddressDto.builder()
                .city("sa")
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createAddressDtoValidator,createAddressDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]: ")
                .hasMessageContaining("city: have wrong format");
    }



    @Test
    @DisplayName("when city contains illegal characters")
    public void test6() {
        var createAddressDtoValidator = new CreateAddressDtoValidator();
        var createAddressDto = CreateAddressDto.builder()
                .city("&%$ alesławice")
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createAddressDtoValidator,createAddressDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]: ")
                .hasMessageContaining("city: have wrong format");
    }

    @Test
    @DisplayName("when zip code is correct")
    public void test7() {
        var createAddressDtoValidator = new CreateAddressDtoValidator();
        var createAddressDto = CreateAddressDto.builder()
                .zipCode("62-200")
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createAddressDtoValidator,createAddressDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]: ")
                .hasMessageNotContaining("zip code:");
    }

    @Test
    @DisplayName("when city is correct")
    public void test8() {
        var createAddressDtoValidator = new CreateAddressDtoValidator();
        var createAddressDto = CreateAddressDto.builder()
                .city("Gorzkie Pole")
                .build();

        Assertions.assertThatThrownBy(() -> Validator.validate(createAddressDtoValidator,createAddressDto))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]: ")
                .hasMessageNotContaining("city:");
    }

}