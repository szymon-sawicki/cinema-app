package com.cinema.app.domain.cinema_room.dto;

import com.cinema.app.domain.cinema.Cinema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GetCinemaRoomDto {

    private Long id;
    private String name;
    private Integer rows;
    private Integer placeNumber;
    private Cinema cinema;

}
