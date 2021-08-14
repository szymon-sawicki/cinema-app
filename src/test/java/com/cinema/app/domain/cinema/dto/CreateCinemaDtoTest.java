package com.cinema.app.domain.cinema.dto;

import com.cinema.app.domain.address.dto.CreateAddressDto;
import com.cinema.app.domain.cinema.Cinema;
import com.cinema.app.domain.cinema_room.CinemaRoom;
import com.cinema.app.domain.cinema_room.dto.CreateCinemaRoomDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CreateCinemaDtoTest {

    @Test
    @DisplayName("when conversion to cinema is succesful")
    public void test1() {

        var name = "Andrew";
        var createAddressDto = CreateAddressDto.builder().build();
        var cinemaRooms = List.of(CreateCinemaRoomDto.builder().name("aa").build());

        var createCinemaDto = CreateCinemaDto.builder()
                .name(name)
                .createAddressDto(createAddressDto)
                .cinemaRoomDtos(cinemaRooms)
                .build();

        var cinema = createCinemaDto.toCinema();

        var expectedCinema = Cinema.builder()
                .name(name)
                .build();

        assertThat(cinema).isEqualTo(expectedCinema);



    }

}
