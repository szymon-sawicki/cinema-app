package com.cinema.app.infrastructure.persistence.entity;

import com.cinema.app.domain.cinema.Cinema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@ToString
@EqualsAndHashCode

public class CinemaEntity {

    private Long id;
    private String name;
    private Long addressId;


    public Cinema toCinema() {
        return Cinema.builder()
                .id(id)
                .name(name)
                .addressId(addressId)
                .build();
    }



}
