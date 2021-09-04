package com.cinema.app.domain.user.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.configs.validator.ValidatorException;
import com.cinema.app.domain.user.dto.CreateUpdateUserDto;
import com.cinema.app.domain.user.type.Gender;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CreateUpdateUserDtoValidatorTest {

    @Test
    @DisplayName("when create user dto is null")
    public void test1() {

        Assertions.assertThatThrownBy(() -> Validator.validate(new CreateUpdateUserDtoValidator(),null))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("create user dto: is null");

    }

    @Test
    @DisplayName("when name have wrong format")
    public void test2() {

        var user = CreateUpdateUserDto.builder()
                .name("asd%$ ldfkk")
                .build();

        var createUsrDtoValidator = new CreateUpdateUserDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createUsrDtoValidator,user))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("name: have wrong format");
    }

    @Test
    @DisplayName("when name is null")
    public void test3() {

        var user = CreateUpdateUserDto.builder()
                .name(null)
                .build();

        var createUserDtoValidator = new CreateUpdateUserDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createUserDtoValidator,user))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("name: is null");
    }

    @Test
    @DisplayName("when password contains whitespaces")
    public void test4() {


        var user = CreateUpdateUserDto.builder()
                .password("my secretPassword")
                .build();

        var createUserDtoValidator = new CreateUpdateUserDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createUserDtoValidator,user))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("password: have wrong format");

    }

    @Test
    @DisplayName("when password is too short")
    public void test5() {


        var user = CreateUpdateUserDto.builder()
                .password("pass")
                .build();

        var createUserDtoValidator = new CreateUpdateUserDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createUserDtoValidator,user))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("password: have wrong format");

    }

    @Test
    @DisplayName("when password is null")
    public void test6() {

        var user = CreateUpdateUserDto.builder()
                .password(null)
                .build();

        var createUserDtoValidator = new CreateUpdateUserDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createUserDtoValidator,user))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("password: is null");

    }

    @Test
    @DisplayName("when mail is null")
    public void test7() {

        var user = CreateUpdateUserDto.builder()
                .mail(null)
                .build();

        var createUserDtoValidator = new CreateUpdateUserDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createUserDtoValidator,user))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("mail: is null");
    }

    @Test
    @DisplayName("when mail have wrong format (without @)")
    public void test8() {

        var user = CreateUpdateUserDto.builder()
                .mail("example_mail.gmail.com")
                .build();

        var createUserDtoValidator = new CreateUpdateUserDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createUserDtoValidator,user))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("mail: have wrong format");
    }

    @Test
    @DisplayName("when mail have wrong format (incorrect characters)")
    public void test9() {

        var user = CreateUpdateUserDto.builder()
                .mail("example@@mail@gmail.com")
                .build();

        var createUserDtoValidator = new CreateUpdateUserDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createUserDtoValidator,user))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("mail: have wrong format");
    }

    @Test
    @DisplayName("when mail is correct")
    public void test10() {

        var user = CreateUpdateUserDto.builder()
                .mail("szymon.sawicki@gmail.com")
                .build();

        var createUserDtoValidator = new CreateUpdateUserDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createUserDtoValidator,user))
                .hasMessageNotContaining("mail");
    }

    @Test
    @DisplayName("when username is null")
    public void test11() {
        var user = CreateUpdateUserDto.builder()
                .username(null)
                .build();

        var createUserDtoValidator = new CreateUpdateUserDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createUserDtoValidator,user))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("username: is null");
    }

    @Test
    @DisplayName("when username contains illegal characters")
    public void test12() {
        var user = CreateUpdateUserDto.builder()
                .username("olabiooga%@)")
                .build();

        var createUserDtoValidator = new CreateUpdateUserDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createUserDtoValidator,user))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("username: have wrong format");
    }

    @Test
    @DisplayName("when username is too short")
    public void test13() {
        var user = CreateUpdateUserDto.builder()
                .username("kiti")
                .build();

        var createUserDtoValidator = new CreateUpdateUserDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createUserDtoValidator,user))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("username: have wrong format");
    }

    @Test
    @DisplayName("when birth date is null")
    public void test14() {
        var user = CreateUpdateUserDto.builder()
                .birthDate(null)
                .build();

        var createUserDtoValidator = new CreateUpdateUserDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createUserDtoValidator,user))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("birth date: is null");
    }

    @Test
    @DisplayName("when birth date is in the future")
    public void test15() {
        var user = CreateUpdateUserDto.builder()
                .birthDate(LocalDate.now().plusYears(3))
                .build();

        var createUserDtoValidator = new CreateUpdateUserDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createUserDtoValidator,user))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("birth date: is out of range");
    }

    @Test
    @DisplayName("when birth date is one year ago")
    public void test16() {
        var user = CreateUpdateUserDto.builder()
                .birthDate(LocalDate.now().plusYears(3))
                .build();

        var createUserDtoValidator = new CreateUpdateUserDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createUserDtoValidator,user))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("birth date: is out of range");
    }

    @Test
    @DisplayName("when gender is null")
    public void test17() {
        var user = CreateUpdateUserDto.builder()
                .gender(null)
                .build();

        var createUserDtoValidator = new CreateUpdateUserDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createUserDtoValidator,user))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("gender: is null");
    }

    @Test
    @DisplayName("when user is correct")
    public void test18() {

        var user = CreateUpdateUserDto.builder()
                .birthDate(LocalDate.now().minusYears(30))
                .username("aleksandro")
                .password("eeeeellss")
                .name("peterinko")
                .mail("aleksandro@peterinko.pl")
                .gender(Gender.MALE)
                .build();

        var validator = new CreateUpdateUserDtoValidator();

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(()->Validator.validate(validator,user));

    }

}
