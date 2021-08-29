package com.cinema.app.domain.cinema_room;

import com.cinema.app.domain.cinema_room.dto.GetCinemaRoomDto;
import com.cinema.app.infrastructure.persistence.entity.CinemaRoomEntity;
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
                .rowsNum(rows)
                .placeNumber(placeNumber)
                .cinemaId(cinemaId)
                .build();

        var getCinemaRoomDto = cinemaRoom.toGetCinemaRoomDto();

        var expectedGetCinemaRomDto = GetCinemaRoomDto.builder()
                .name(name)
                .rowsNum(rows)
                .placeNumber(placeNumber)
                .cinemaId(cinemaId)
                .build();

        assertThat(getCinemaRoomDto).isEqualTo(expectedGetCinemaRomDto);

    }

    @Test
    @DisplayName("when cinema room wit new cinema id is returned")
    public void test2() {

        var name = "great room";
        var rows = 15;
        var placeNumber = 20;
        var cinemaId = 1L;

        var newCinemaId = 3L;

        var cinemaRoom = CinemaRoom.builder()
                .name(name)
                .rowsNum(rows)
                .placeNumber(placeNumber)
                .cinemaId(cinemaId)
                .build();

        var expectedCinemaRoom = CinemaRoom.builder()
                .name(name)
                .rowsNum(rows)
                .placeNumber(placeNumber)
                .cinemaId(newCinemaId)
                .build();

        assertThat(cinemaRoom.withCinemaId(newCinemaId))
                .isEqualTo(expectedCinemaRoom);

    }

    @Test
    @DisplayName("when coversion to entity is correct")
    public void test3() {

        var id = 5L;
        var name = "great room";
        var rows = 15;
        var placeNumber = 20;
        var cinemaId = 1L;

        var newCinemaId = 3L;

        var cinemaRoom = CinemaRoom.builder()
                .id(id)
                .name(name)
                .rowsNum(rows)
                .placeNumber(placeNumber)
                .cinemaId(cinemaId)
                .build();

        var cinemaRoomEntity = CinemaRoomEntity.builder()
                .id(id)
                .name(name)
                .rowsNum(rows)
                .placeNumber(placeNumber)
                .cinemaId(cinemaId)
                .build();

        assertThat(cinemaRoom.toEntity()).isEqualTo(cinemaRoomEntity);

    }



}
