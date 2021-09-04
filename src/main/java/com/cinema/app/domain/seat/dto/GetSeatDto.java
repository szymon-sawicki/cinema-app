package com.cinema.app.domain.seat.dto;

import com.cinema.app.domain.cinema_room.CinemaRoom;
import com.cinema.app.domain.seat.type.SeatType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder

public class GetSeatDto {

    private Long id;
    private Integer rowNum;
    private Integer place;
    private boolean isBooked = false;
    private Long cinemaRoomId;

}
