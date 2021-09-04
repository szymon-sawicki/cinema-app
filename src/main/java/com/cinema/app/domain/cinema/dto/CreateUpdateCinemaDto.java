package com.cinema.app.domain.cinema.dto;

import com.cinema.app.domain.address.dto.CreateUpdateAddressDto;
import com.cinema.app.domain.cinema.Cinema;
import com.cinema.app.domain.cinema_room.dto.CreateUpdateCinemaRoomDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateUpdateCinemaDto {

    private String name;
    private CreateUpdateAddressDto createAddressDto;
    private List<CreateUpdateCinemaRoomDto> cinemaRoomDtos;

    public Cinema toCinema() {
        return Cinema.builder()
                .name(name)
                .build();
    }

}
