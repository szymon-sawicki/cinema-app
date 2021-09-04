package com.cinema.app.domain.ticket.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.ticket.dto.CreateUpdateTicketDto;
import com.cinema.app.domain.user.dto.validator.CreateUpdateUserDtoValidator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CreateUpdateTicketDtoValidator implements Validator<CreateUpdateTicketDto> {

    @Override
    public Map<String, String> validate(CreateUpdateTicketDto createUpdateTicketDto) {
        var errors = new HashMap<String, String>();

        if (createUpdateTicketDto == null) {
            errors.put("create ticket dto", "is null");
            return errors;
        }

        if (createUpdateTicketDto.getScreeningId() == null) {
            errors.put("screening id", "is null");
        } else if (createUpdateTicketDto.getScreeningId() <= 0) {
            errors.put("screening id", "is 0 or negative");
        }

        if (createUpdateTicketDto.getCreateUserDto() == null) {
            errors.put("create user dto", "is null");
        } else {
            errors.putAll(new CreateUpdateUserDtoValidator().validate(createUpdateTicketDto.getCreateUserDto()));
        }

        if (createUpdateTicketDto.getSeats() == null) {
            errors.put("seats", "is null");
        } else if(createUpdateTicketDto.getSeats().isEmpty()) {
            errors.put("seats","are empty");
        }

        if (createUpdateTicketDto.getDiscount() == null) {
            errors.put("discount", "is null");
        } else {
            if (createUpdateTicketDto.getDiscount() > 100) {
                errors.put("discount", "is greater than 100%");
            }
            if (createUpdateTicketDto.getDiscount() < 0) {
                errors.put("discount", "is negative");
            }
        }

        if (createUpdateTicketDto.getPrice() == null) {
            errors.put("price", "is null");
        } else if (createUpdateTicketDto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            errors.put("price", "is negative");
        }

        if (createUpdateTicketDto.getStatus() == null) {
            errors.put("status", "is null");
        }

        return errors;
    }
}
