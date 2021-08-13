package com.cinema.app.domain.cinema.dto;

import com.cinema.app.domain.address.Address;
import com.cinema.app.domain.address.dto.CreateAddressDto;
import com.cinema.app.domain.cinema.Cinema;
import com.cinema.app.domain.cinema_room.dto.CreateCinemaRoomDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateCinemaDto {

    private String name;
    private CreateAddressDto createAddressDto;
    private List<CreateCinemaRoomDto> cinemaRoomDtos;

    public Cinema toCinema() {
        return Cinema.builder()
                .name(name)
                .build();
    }

}
