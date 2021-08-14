package com.cinema.app.domain.cinema_room;

import com.cinema.app.domain.cinema_room.dto.GetCinemaRoomDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CinemaRoomDomainTest {

    @Test
    @DisplayName("when conversion to get cinema room dto is correct")
    public void test1() {

        var name = "great room";
        var rows = 15;
        var placeNumber = 20;
        var cinemaId = 3L;

        var cinemaRoom = CinemaRoom.builder()
                .name(name)
                .rows(rows)
                .placeNumber(placeNumber)
                .cinemaId(cinemaId)
                .build();

        var getCinemaRoomDto = cinemaRoom.toGetCinemaRoomDto();

        var expectedGetCinemaRomDto = GetCinemaRoomDto.builder()
                .name(name)
                .rows(rows)
                .placeNumber(placeNumber)
                .cinemaId(cinemaId)
                .build();

        assertThat(getCinemaRoomDto).isEqualTo(expectedGetCinemaRomDto);

    }

}
