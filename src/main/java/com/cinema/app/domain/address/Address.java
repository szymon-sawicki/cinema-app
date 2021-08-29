package com.cinema.app.domain.address;

import com.cinema.app.domain.address.dto.GetAddressDto;
import com.cinema.app.domain.cinema.dto.GetCinemaDto;
import com.cinema.app.infrastructure.persistence.entity.AddressEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString

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

    public AddressEntity toEntity() {
        return AddressEntity.builder()
                .id(id)
                .city(city)
                .street(street)
                .zipCode(zipCode)
                .houseNumber(houseNumber)
                .build();
    }


}
