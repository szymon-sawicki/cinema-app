package com.cinema.app.domain.cinema.dto;

import com.cinema.app.domain.address.Address;
import com.cinema.app.domain.cinema.Cinema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateCinemaDto {

    private String name;
    private Address address;

    public Cinema toCinema() {
        return Cinema.builder()
                .name(name)
                .address(address)
                .build();
    }

}
