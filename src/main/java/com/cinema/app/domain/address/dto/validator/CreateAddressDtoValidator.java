package com.cinema.app.domain.address.dto.validator;

import com.cinema.app.domain.address.dto.CreateAddressDto;
import com.cinema.app.domain.configs.validator.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * implementation of Validator interface used to validate CreateAddressDto objects
 * @see Validator
 * @author Szymon Sawicki
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
        } else if (createAddressDto.getStreet().length() > 30) {
            errors.put("street", "is too long");
        }

        if (createAddressDto.getHouseNumber() == null) {
            errors.put("house number", "is null");
        } else if(createAddressDto.getHouseNumber().replaceAll("[^0-9/]+","").length() > 0) {
            errors.put("house number","have wrong format");
        }

        if(createAddressDto.getCity() == null) {
            errors.put("city","is null");
        } else {
            if(createAddressDto.getCity().replaceAll("[^A-Za-z-]+","").length() > 0){
                errors.put("city","have wrong format");
            }
            if(createAddressDto.getCity().length() > 20) {
                errors.put("city","is too long");
            }
        }

        if(createAddressDto.getZipCode() == null) {
            errors.put("zip code","is null");
        } else {
            var zipCode = createAddressDto.getZipCode();
            if(zipCode.replaceAll("[^0-9-]+","").length() > 0) {
                errors.put("zip code","have wrong format");
            }
            if(zipCode.length() > 6) {
                errors.put("zip code","is too long");
            }
        }

        return errors;
    }
}
