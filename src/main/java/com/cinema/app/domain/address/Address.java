package com.cinema.app.domain.address;

import com.cinema.app.domain.address.dto.GetAddressDto;
import com.cinema.app.domain.cinema.dto.GetCinemaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode

/**
 *  class representing address of cinema
 * @Author Szymon Sawicki
 */

public class Address {

    Long id;
    String street;
    String houseNumber;
    String city;
    String zipCode;

    public GetAddressDto toGetAddressDto() {
        return GetAddressDto.builder()
                .id(id)
                .street(street)
                .houseNumber(houseNumber)
                .city(city)
                .zipCode(zipCode)
                .build();
    }


}
