package com.cinema.app.domain.cinema_room;

import com.cinema.app.domain.cinema.Cinema;

import com.cinema.app.domain.cinema_room.dto.GetCinemaRoomDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode

/**
 * class representing one room of the cinema
 * @author Szymon Sawicki
 */

public class CinemaRoom {

    Long id;
    String name;
    Integer rows;
    Integer placeNumber;
    Cinema cinema;


    public GetCinemaRoomDto toGetCinemaRoomDto() {
        return GetCinemaRoomDto.builder()
                .id(id)
                .name(name)
                .rows(rows)
                .placeNumber(placeNumber)
                .cinema(cinema)
                .build();
    }

}
