package com.cinema.app.domain.user.dto.validator;

import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.configs.validator.ValidatorException;
import com.cinema.app.domain.user.dto.CreateUpdateUserDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CreateUpdateUserDtoValidator implements Validator<CreateUpdateUserDto> {
    @Override
    public Map<String, String> validate(CreateUpdateUserDto createUserDto) {

        var errors = new HashMap<String, String>();

        if (createUserDto == null) {
            errors.put("create user dto", "is null");
            return errors;
        }

        if (createUserDto.getName() == null) {
            errors.put("name", "is null");
        } else if (!createUserDto.getName().matches("[\\w\\s\\-'.]{5,20}+")) {
            errors.put("name", "have wrong format");
        }

        if(createUserDto.getRole() == null) {
            errors.put("role","is null");
        }

        if (createUserDto.getPassword() == null) {
            errors.put("password", "is null");
        } else if (!createUserDto.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*]).{8,}$")) {
            errors.put("password", "have wrong format");
        }

        if (createUserDto.getMail() == null) {
            errors.put("mail", "is null");
        } else if (!createUserDto.getMail().matches("[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            errors.put("mail", "have wrong format");
        }

        if (createUserDto.getUsername() == null) {
            errors.put("username", "is null");
        } else if (!createUserDto.getUsername().matches("[\\w\\s\\-_.]{5,20}+")) {
            errors.put("username", "have wrong format");
        }

        if (createUserDto.getBirthDate() == null) {
            errors.put("birth date", "is null");
        } else {
            if (createUserDto.getBirthDate().isAfter(LocalDate.now().minusYears(10))) {
                errors.put("birth date", "is out of range");
            }
        }

        if(createUserDto.getGender() == null) {
            errors.put("gender","is null");
        }
            return errors;
        }
    }

