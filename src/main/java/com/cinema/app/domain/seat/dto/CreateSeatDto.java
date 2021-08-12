package com.cinema.app.domain.seat.dto;

import com.cinema.app.domain.cinema_room.CinemaRoom;
import com.cinema.app.domain.seat.Seat;
import com.cinema.app.domain.seat.type.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateSeatDto {

    private Integer rowNum;
    private Integer place;
    private SeatType seatType;
    private CinemaRoom cinemaRoom;

    public Seat toSeat() {
        return Seat.builder()
                .rowNum(rowNum)
                .place(place)
                .seatType(seatType)
                .cinemaRoom(cinemaRoom)
                .build();
    }

}
