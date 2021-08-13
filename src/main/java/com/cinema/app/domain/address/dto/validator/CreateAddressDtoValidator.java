package com.cinema.app.domain.address.dto.validator;

import com.cinema.app.domain.address.dto.CreateAddressDto;
import com.cinema.app.domain.configs.validator.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * implementation of Validator interface used to validate CreateAddressDto objects
 *
 * @author Szymon Sawicki
 * @see Validator
 */

public class CreateAddressDtoValidator implements Validator<CreateAddressDto> {

    @Override
    public Map<String, String> validate(CreateAddressDto createAddressDto) {

        var errors = new HashMap<String, String>();

        if (createAddressDto == null) {
            errors.put("create address dto", "is null");
        }

        if (createAddressDto.getStreet() == null) {
            errors.put("street", "is null");
        } else if (createAddressDto.getStreet().matches("[A-Za-z-,'.]{3,20}+")) {
            errors.put("street","have wrong format");
        }

        if (createAddressDto.getHouseNumber() == null) {
            errors.put("house number", "is null");
        } else if (!createAddressDto.getHouseNumber().matches("[^0-9/]*")) {
            errors.put("house number", "have wrong format");
        }

        if (createAddressDto.getCity() == null) {
            errors.put("city", "is null");
        } else {
            if (!createAddressDto.getCity().matches("[A-Za-z-]{3,20}+")) {
                errors.put("city", "have wrong format");
            }
        }

        if (createAddressDto.getZipCode() == null) {
            errors.put("zip code", "is null");
        } else {
            if (!createAddressDto.getZipCode().matches("[^0-9-]{2,6}+")) {
                errors.put("zip code", "have wrong format");
            }
        }

        return errors;
    }
}
