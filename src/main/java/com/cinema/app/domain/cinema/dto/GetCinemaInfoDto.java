package com.cinema.app.domain.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GetCinemaInfoDto {

    String name;
    String city;
    String street;
    String houseNumber;
    String zipCode;


}
