package com.cinema.app.domain.seat;

import com.cinema.app.domain.cinema_room.CinemaRoom;
import com.cinema.app.domain.seat.dto.GetSeatDto;
import com.cinema.app.domain.seat.type.SeatType;
import com.cinema.app.infrastructure.persistence.entity.SeatEntity;
import lombok.*;
import org.jdbi.v3.core.mapper.Nested;

/*@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode*/

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString

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

    public SeatEntity toEntity() {
        return SeatEntity.builder()
                .id(id)
                .rowNum(rowNum)
                .place(place)
                .seatType(seatType)
                .cinemaRoomId(cinemaRoomId)
                .build();
    }

}
