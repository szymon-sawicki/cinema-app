package com.cinema.app.infrastructure.persistence.entity.view;

import com.cinema.app.domain.cinema.dto.GetCinemaInfoDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@ToString
@EqualsAndHashCode

public class CinemaInfo {

    String name;
    String city;
    String street;
    String houseNumber;
    String zipCode;

    public GetCinemaInfoDto toGetCinemaInfoDto() {
        return GetCinemaInfoDto.builder()
                .name(name)
                .city(city)
                .street(street)
                .houseNumber(houseNumber)
                .zipCode(zipCode)
                .build();
    }

}
