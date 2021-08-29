package com.cinema.app.infrastructure.persistence.entity;

import com.cinema.app.domain.address.Address;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@ToString

public class AddressEntity {

    private Long id;
    private String street;
    private String houseNumber;
    private String city;
    private  String zipCode;


    public Address toAddress() {
        return Address.builder()
                .id(id)
                .city(city)
                .street(street)
                .zipCode(zipCode)
                .houseNumber(houseNumber)
                .build();
    }


}
