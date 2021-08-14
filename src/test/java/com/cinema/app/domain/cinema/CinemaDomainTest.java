package com.cinema.app.domain.cinema;

import com.cinema.app.domain.address.dto.GetAddressDto;
import com.cinema.app.domain.cinema.dto.GetCinemaDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CinemaDomainTest {

    @Test
    @DisplayName("when conversion to get cinema dto is correct")
    public void test1() {

        var id = 1L;
        var name = "Helios";
        var addressId = 1L;

        var cinema = Cinema.builder()
                .id(id)
                .name(name)
                .addressId(addressId)
                .build();

        var getCinemaDto = cinema.toGetCinemaDto();

        var expectedCinemaDto = GetCinemaDto.builder()
                .id(id)
                .name("Helios")
                .addressId(2L)
                .build();

        Assertions.assertThat(getCinemaDto).isEqualTo(expectedCinemaDto);
    }
}