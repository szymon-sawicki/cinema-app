package com.cinema.app.application.service;

import com.cinema.app.domain.address.dto.CreateAddressDto;
import com.cinema.app.domain.cinema.dto.CreateCinemaDto;
import com.cinema.app.infrastructure.persistence.AddressDao;
import com.cinema.app.infrastructure.persistence.CinemaDao;
import com.cinema.app.infrastructure.persistence.CinemaRoomDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CinemasServiceTest {

    @Mock
    private CinemaDao cinemaDao;
    @Mock
    private AddressDao addressDao;
    @Mock
    private CinemaRoomDao cinemaRoomDao;

    @InjectMocks
    private CinemasService cinemasService;

    @Test
    @DisplayName("when address of cinema already exists in db")
    public void test1() {

        var street = "Wagramer Strasse";
        var city = "Graz";
        var houseNumber = "23/5";
        var zipCode = "1010";

        var createAddressDto = CreateAddressDto.builder()
                .street(street)
                .city(city)
                .houseNumber(houseNumber)
                .zipCode(zipCode)
                .build();


        var createCinemaDto = CreateCinemaDto.builder()
                .createAddressDto(createAddressDto)
                .cinemaRoomDtos(List.of())
                .build();

    }



}
