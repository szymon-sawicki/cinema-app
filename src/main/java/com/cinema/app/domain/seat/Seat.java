package com.cinema.app.domain.seat;

import com.cinema.app.domain.cinema_room.CinemaRoom;
import com.cinema.app.domain.seat.dto.GetSeatDto;
import com.cinema.app.domain.seat.type.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode

/**
 * class representing one seat in CinemaRoom
 * @author Szymon Sawicki
 */

public class Seat {

    Long id;
    Integer rowNum;
    Integer place;
    SeatType seatType;
    Long cinemaRoomId;


    public GetSeatDto toGetSeatDto() {
        return GetSeatDto.builder()
                .id(id)
                .rowNum(rowNum)
                .place(place)
                .seatType(seatType)
                .cinemaRoomId(cinemaRoomId)
                .build();
    }

}
