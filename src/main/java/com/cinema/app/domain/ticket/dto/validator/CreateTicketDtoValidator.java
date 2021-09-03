package com.cinema.app.domain.ticket.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.ticket.dto.CreateTicketDto;
import com.cinema.app.domain.user.dto.validator.CreateUserDtoValidator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CreateTicketDtoValidator implements Validator<CreateTicketDto> {

    @Override
    public Map<String, String> validate(CreateTicketDto createTicketDto) {
        var errors = new HashMap<String, String>();

        if (createTicketDto == null) {
            errors.put("create ticket dto", "is null");
            return errors;
        }

        if (createTicketDto.getScreeningId() == null) {
            errors.put("screening id", "is null");
        } else if (createTicketDto.getScreeningId() <= 0) {
            errors.put("screening id", "is 0 or negative");
        }

        if (createTicketDto.getCreateUserDto() == null) {
            errors.put("create user dto", "is null");
        } else {
            errors.putAll(new CreateUserDtoValidator().validate(createTicketDto.getCreateUserDto()));
        }

        if (createTicketDto.getSeatId() == null) {
            errors.put("seat id", "is null");
        } else if (createTicketDto.getSeatId() <= 0) {
            errors.put("seat id", "is 0 or negative");
        }

        if (createTicketDto.getDiscount() == null) {
            errors.put("discount", "is null");
        } else {
            if (createTicketDto.getDiscount() > 100) {
                errors.put("discount", "is greater than 100%");
            }
            if (createTicketDto.getDiscount() < 0) {
                errors.put("discount", "is negative");
            }
        }

        if(createTicketDto.getPrice() == null) {
            errors.put("price","is null");
        } else if (createTicketDto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            errors.put("price", "is negative");
        }

        if (createTicketDto.getStatus() == null) {
            errors.put("status", "is null");
        }

        return errors;
    }
}
