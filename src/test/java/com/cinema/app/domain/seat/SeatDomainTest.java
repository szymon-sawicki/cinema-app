package com.cinema.app.domain.seat;

import com.cinema.app.domain.seat.dto.GetSeatDto;
import com.cinema.app.domain.seat.type.SeatType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SeatDomainTest {

    @Test
    @DisplayName("when conversion to get seat dto test is correct")
    public void test1() {

        var rowNum = 2;
        var place = 3;
        var seatType = SeatType.SOFA;
        var cinemaRoomId = 5L;

        var seat = Seat.builder()
                .rowNum(rowNum)
                .place(place)
                .seatType(seatType)
                .cinemaRoomId(cinemaRoomId)
                .build();

        var getSeatDto = seat.toGetSeatDto();

        var expectedGetSeatDao = GetSeatDto.builder()
                .rowNum(rowNum)
                .place(place)
                .seatType(seatType)
                .cinemaRoomId(cinemaRoomId)
                .build();

        Assertions.assertThat(getSeatDto).isEqualTo(expectedGetSeatDao);


    }

}
