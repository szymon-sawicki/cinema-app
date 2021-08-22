package com.cinema.app.domain.cinema_room.dto;

import com.cinema.app.domain.cinema_room.CinemaRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateCinemaRoomDto {

    private String name;
    private Integer rowsNum;
    private Integer placeNumber;
    private Long cinemaId;

    public CinemaRoom toCinemaRoom() {
        return CinemaRoom.builder()
                .name(name)
                .rowsNum(rowsNum)
                .placeNumber(placeNumber)
                .cinemaId(cinemaId)
                .build();
    }

}
