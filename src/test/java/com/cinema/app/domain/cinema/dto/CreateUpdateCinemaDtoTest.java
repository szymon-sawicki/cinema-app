package com.cinema.app.domain.cinema.dto;

import com.cinema.app.domain.address.dto.CreateUpdateAddressDto;
import com.cinema.app.domain.cinema.Cinema;
import com.cinema.app.domain.cinema_room.dto.CreateUpdateCinemaRoomDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CreateUpdateCinemaDtoTest {

    @Test
    @DisplayName("when conversion to cinema is succesful")
    public void test1() {

        var name = "Andrew";
        var createAddressDto = CreateUpdateAddressDto.builder().build();
        var cinemaRooms = List.of(CreateUpdateCinemaRoomDto.builder().name("aa").build());

        var createCinemaDto = CreateUpdateCinemaDto.builder()
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
