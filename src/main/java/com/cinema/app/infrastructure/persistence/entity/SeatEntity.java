package com.cinema.app.infrastructure.persistence.entity;

import com.cinema.app.domain.seat.Seat;
import com.cinema.app.domain.seat.type.SeatType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@EqualsAndHashCode

public class SeatEntity {
    private Long id;
    private Integer rowNum;
    private Integer place;
    private SeatType seatType;
    private Long cinemaRoomId;

    public Seat toSeat() {
        return Seat.builder()
                .id(id)
                .rowNum(rowNum)
                .place(place)
                .seatType(seatType)
                .cinemaRoomId(cinemaRoomId)
                .build();
    }

}
