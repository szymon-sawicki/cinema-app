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

public class CreateUpdateSeatDto {

    private Integer rowNum;
    private Integer place;
    private SeatType seatType;
    private Long cinemaRoomId;

    public Seat toSeat() {
        return Seat.builder()
                .rowNum(rowNum)
                .place(place)
                .seatType(seatType)
                .cinemaRoomId(cinemaRoomId)
                .build();
    }

}
