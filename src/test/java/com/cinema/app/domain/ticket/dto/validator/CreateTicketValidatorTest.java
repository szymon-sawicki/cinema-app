package com.cinema.app.domain.ticket.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.configs.validator.ValidatorException;
import com.cinema.app.domain.ticket.dto.CreateTicketDto;
import com.cinema.app.domain.ticket.type.Status;
import com.cinema.app.domain.user.dto.CreateUserDto;
import com.cinema.app.domain.user.type.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class CreateTicketValidatorTest {

    @Test
    @DisplayName("when create ticket dto is null")
    public void test1() {

        var createTicketValidator = new CreateTicketDtoValidator();

        assertThatThrownBy(() -> Validator.validate(createTicketValidator, null))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("create ticket dto", "is null");
    }

    @Test
    @DisplayName("when screening id is null")
    public void test2() {

        var createTicketValidator = new CreateTicketDtoValidator();

        var ticket = CreateTicketDto.builder()
                .screeningId(null)
                .build();


        assertThatThrownBy(() -> Validator.validate(createTicketValidator, ticket))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("screening id", "is null");
    }

    @Test
    @DisplayName("when screening id is negative")
    public void test3() {

        var createTicketValidator = new CreateTicketDtoValidator();

        var ticket = CreateTicketDto.builder()
                .screeningId(-5L)
                .build();


        assertThatThrownBy(() -> Validator.validate(createTicketValidator, ticket))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("screening id", "is 0 or negative");
    }

    @Test
    @DisplayName("when price is negative")
    public void test4() {

        var createTicketValidator = new CreateTicketDtoValidator();

        var ticket = CreateTicketDto.builder()
                .price(new BigDecimal("-60"))
                .build();

        assertThatThrownBy(() -> Validator.validate(createTicketValidator, ticket))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("price", "is negative");
    }

    @Test
    @DisplayName("when create user dto is null")
    public void test5() {

        var createTicketValidator = new CreateTicketDtoValidator();

        var ticket = CreateTicketDto.builder()
                .createUserDto(null)
                .build();

        assertThatThrownBy(() -> Validator.validate(createTicketValidator, ticket))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("create user dto", "is null");
    }

    @Test
    @DisplayName("when seat id is null")
    public void test6() {
        var createTicketValidator = new CreateTicketDtoValidator();

        var ticket = CreateTicketDto.builder()
                .seatId(null)
                .build();

        assertThatThrownBy(() -> Validator.validate(createTicketValidator, ticket))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("seat id", "is null");
    }

    @Test
    @DisplayName("when seat id is negative")
    public void test7() {
        var createTicketValidator = new CreateTicketDtoValidator();

        var ticket = CreateTicketDto.builder()
                .seatId(-10L)
                .build();

        assertThatThrownBy(() -> Validator.validate(createTicketValidator, ticket))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("seat id", "is 0 or negative");
    }

    @Test
    @DisplayName("when price is null")
    public void test8() {

        var createTicketValidator = new CreateTicketDtoValidator();

        var ticket = CreateTicketDto.builder()
                .price(null)
                .build();

        assertThatThrownBy(() -> Validator.validate(createTicketValidator, ticket))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("price", "is null");
    }

    @Test
    @DisplayName("when discount is greater than 100")
    public void test9() {

        var createTicketValidator = new CreateTicketDtoValidator();

        var ticket = CreateTicketDto.builder()
                .discount(125)
                .build();

        assertThatThrownBy(() -> Validator.validate(createTicketValidator, ticket))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("discount", "is greater than 100%");
    }

    @Test
    @DisplayName("when discount is negative")
    public void test10() {

        var createTicketValidator = new CreateTicketDtoValidator();

        var ticket = CreateTicketDto.builder()
                .discount(-30)
                .build();

        assertThatThrownBy(() -> Validator.validate(createTicketValidator, ticket))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("discount", "is negative");
    }

    @Test
    @DisplayName("when discount is null")
    public void test11() {

        var createTicketValidator = new CreateTicketDtoValidator();

        var ticket = CreateTicketDto.builder()
                .discount(null)
                .build();

        assertThatThrownBy(() -> Validator.validate(createTicketValidator, ticket))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("discount", "is null");
    }

    @Test
    @DisplayName("when status is null")
    public void test12() {

        var createTicketValidator = new CreateTicketDtoValidator();

        var ticket = CreateTicketDto.builder()
                .status(null)
                .build();

        assertThatThrownBy(() -> Validator.validate(createTicketValidator, ticket))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:")
                .hasMessageContaining("status", "is null");
    }

    @Test
    @DisplayName("when ticket is correct")
    public void test13() {

        var createTicketValidator = new CreateTicketDtoValidator();

        var user = CreateUserDto.builder()
                .birthDate(LocalDate.now().minusYears(30))
                .username("aleksandro")
                .password("eeeeellss")
                .name("peterinko")
                .mail("aleksandro@peterinko.pl")
                .gender(Gender.MALE)
                .build();

        var ticket = CreateTicketDto.builder()
                .price(new BigDecimal("25"))
                .discount(20)
                .screeningId(4L)
                .seatId(45L)
                .status(Status.CONFIRMED)
                .createUserDto(user)
                .build();

        assertDoesNotThrow(() -> Validator.validate(createTicketValidator,ticket));

    }
}
