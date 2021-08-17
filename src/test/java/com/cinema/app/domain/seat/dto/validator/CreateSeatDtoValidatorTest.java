package com.cinema.app.domain.seat.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.configs.validator.ValidatorException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateSeatDtoValidatorTest {

    @Test
    @DisplayName("when create seat dto is null")
    public void test1() {

        var createSeatDtoValidator = new CreateSeatDtoValidator();

        Assertions.assertThatThrownBy(() -> Validator.validate(createSeatDtoValidator,null))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS]:");

    }

}
