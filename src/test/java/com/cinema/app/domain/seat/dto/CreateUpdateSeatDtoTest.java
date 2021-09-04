package com.cinema.app.domain.seat.dto;

import com.cinema.app.domain.seat.Seat;
import com.cinema.app.domain.seat.type.SeatType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateUpdateSeatDtoTest {

    @Test
    @DisplayName("when conversion to seat is correct")
    public void test1() {

        var rowNum = 2;
        var place = 3;
        var seatType = SeatType.SOFA;
        var cinemaRoomId = 5L;

        var createSeatDto = CreateUpdateSeatDto.builder()
                .rowNum(rowNum)
                .place(place)
                .seatType(seatType)
                .cinemaRoomId(cinemaRoomId)
                .build();

        var seat = createSeatDto.toSeat();

        var expectedSeat = Seat.builder()
                .rowNum(rowNum)
                .place(place)
                .seatType(seatType)
                .cinemaRoomId(cinemaRoomId)
                .build();

    }

}
