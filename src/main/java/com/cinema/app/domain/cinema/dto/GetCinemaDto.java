package com.cinema.app.domain.cinema.dto;

import com.cinema.app.domain.address.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GetCinemaDto {

    private Long id;
    private String name;
    private Address address;

}
