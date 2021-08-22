package com.cinema.app.domain.cinema_room.dto;

import com.cinema.app.domain.cinema_room.CinemaRoom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateCinemaRoomDtoTest {

    @Test
    @DisplayName("when the conversion to CinemaRoom is correct")
    public void test1() {

        var name = "great room";
        var rows = 15;
        var placeNumber = 20;
        var cinemaId = 3L;

        var createCinemaRoomDto = CreateCinemaRoomDto.builder()
                .name(name)
                .rowsNum(rows)
                .placeNumber(placeNumber)
                .cinemaId(cinemaId)
                .build();

        var cinemaRoom = createCinemaRoomDto.toCinemaRoom();

        var expectedCinemaRoom = CinemaRoom.builder()
                .name(name)
                .rowsNum(rows)
                .placeNumber(placeNumber)
                .cinemaId(cinemaId)
                .build();

    }

}
