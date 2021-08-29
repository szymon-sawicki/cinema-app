package com.cinema.app.infrastructure.persistence.entity;

import com.cinema.app.domain.cinema_room.CinemaRoom;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter

public class CinemaRoomEntity {

    private Long id;
    private String name;
    private Integer rowsNum;
    private Integer placeNumber;
    private Long cinemaId;

    public CinemaRoom toCinemaRoom() {
        return CinemaRoom.builder()
                .id(id)
                .name(name)
                .rowsNum(rowsNum)
                .placeNumber(placeNumber)
                .cinemaId(cinemaId)
                .build();
    }

}
