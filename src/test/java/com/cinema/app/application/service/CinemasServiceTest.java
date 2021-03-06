package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.CinemaServiceException;
import com.cinema.app.domain.address.Address;
import com.cinema.app.domain.address.dto.CreateUpdateAddressDto;
import com.cinema.app.domain.cinema.Cinema;
import com.cinema.app.domain.cinema.dto.CreateUpdateCinemaDto;
import com.cinema.app.domain.cinema.dto.GetCinemaDto;
import com.cinema.app.domain.cinema.dto.GetCinemaInfoDto;
import com.cinema.app.domain.cinema_room.dto.CreateUpdateCinemaRoomDto;
import com.cinema.app.infrastructure.persistence.dao.*;
import com.cinema.app.infrastructure.persistence.entity.AddressEntity;
import com.cinema.app.infrastructure.persistence.entity.CinemaEntity;
import com.cinema.app.infrastructure.persistence.entity.view.CinemaInfo;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CinemasServiceTest {

    @Mock
    private CinemaEntityDao cinemaEntityDao;

    @Mock
    private AddressEntityDao addressEntityDao;

    @Mock
    private CinemaRoomEntityDao cinemaRoomEntityDao;

    @Mock
    private SeatEntityDao seatEntityDao;

    @Mock
    private CinemaInfoDao cinemaInfoDao;

    @InjectMocks
    private CinemasService cinemasService;

 /*   @Test
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

    }*/


    @Test
    @DisplayName("when cinema is searched by name")
    public void test1() {
/*

        when(addressEntityDao.findAllFromCity("berlin"))
                .thenReturn(List.of(
                        Address.builder().city("berlin").street("haupt").zipCode("67-200").houseNumber("234").build(),
                        Address.builder().city("berlin").street("kurzweg").zipCode("67-200").houseNumber("567").build()
                ));
*/
        var city = "Salzburg";
        var street = "Main Street";
        var houseNumber = "130";
        var zipCode = "34-456";
        var name = "Nice cinema";

        var cinema = CinemaInfo.builder()
                .name(name)
                .city(city)
                .street(street)
                .houseNumber(houseNumber)
                .zipCode(zipCode)
                .build();

        when(cinemaInfoDao.findByName(name))
                .thenReturn(Optional.of(cinema));

        var expectedGetCinemaDto = GetCinemaInfoDto.builder()
                .name(name)
                .city(city)
                .street(street)
                .houseNumber(houseNumber)
                .zipCode(zipCode)
                .build();

        assertThat(cinemasService.findByName(name))
                .isEqualTo(expectedGetCinemaDto);
    }

    @Test
    @DisplayName("when new cinema is created")
    public void test2() {

        var addressId = 6L;
        var city = "Berlin";
        var street = "Kurzweg";
        var houseNumber = "325";
        var zipCode = "62-200";
        var cinemaId = 3L;

        var createAddressDto = CreateUpdateAddressDto.builder()
                .city(city)
                .street(street)
                .zipCode(zipCode)
                .houseNumber(houseNumber)
                .build();

        when(addressEntityDao.findAddress(street, houseNumber, city, zipCode))
                .thenReturn(Optional.of(
                        AddressEntity.builder()
                                .id(addressId)
                                .city(city).street(street)
                                .zipCode(zipCode)
                                .houseNumber(houseNumber)
                                .build()
                ));

        var createCinemaRoomDto = CreateUpdateCinemaRoomDto.builder()
                .rowsNum(5)
                .placeNumber(10)
                .cinemaId(cinemaId)
                .name("mmmm")
                .build();

        var createCinemaRoomDto1 = CreateUpdateCinemaRoomDto.builder()
                .rowsNum(8)
                .placeNumber(18)
                .cinemaId(cinemaId)
                .name("oooo")
                .build();

        var createCinemaRoomDtos = List.of(createCinemaRoomDto, createCinemaRoomDto1);

        var cinemaRooms = createCinemaRoomDtos.stream()
                .map(createCinemaRoomDto2 -> createCinemaRoomDto2.toCinemaRoom().toEntity())
                .toList();

        when(cinemaRoomEntityDao.saveAll(cinemaRooms))
                .thenReturn(cinemaRooms);


        var name = "Turbo Cinema";

        var createCinemaDto = CreateUpdateCinemaDto.builder()
                .name(name)
                .createAddressDto(createAddressDto)
                .cinemaRoomDtos(createCinemaRoomDtos)
                .build();

        var cinema = Cinema.builder()
                .id(cinemaId)
                .name(name)
                .addressId(addressId)
                .build();

        when(cinemaEntityDao.save(any(CinemaEntity.class)))
                .thenReturn(Optional.of(cinema.toEntity()));

        assertThat(cinemasService.addCinema(createCinemaDto))
                .isEqualTo(cinema.toGetCinemaDto());


    }

    @Test
    @DisplayName("when cinema id is null (by creating cinema rooms)")
    public void test3() {

        assertThatThrownBy(() -> cinemasService
                .addCinemaRoomsToCinema(null, List.of(CreateUpdateCinemaRoomDto.builder().build()
                )))
                .isInstanceOf(CinemaServiceException.class)
                .hasMessageContaining("cinema id is null");
    }

    @Test
    @DisplayName("when cinema rooms list is null (by creating cinema rooms)")
    public void test4() {

        assertThatThrownBy(() -> cinemasService
                .addCinemaRoomsToCinema(1L, null))
                .isInstanceOf(CinemaServiceException.class)
                .hasMessageContaining("cinema rooms list is null");
    }

    @Test
    @DisplayName("when cinema rooms list is empty (by creating cinema rooms)")
    public void test5() {

        assertThatThrownBy(() -> cinemasService
                .addCinemaRoomsToCinema(1L, Collections.emptyList()))
                .isInstanceOf(CinemaServiceException.class)
                .hasMessageContaining("cinema rooms list is empty");
    }

    @Test
    @DisplayName("when saving cinema rooms is correct")
    public void test6() {

        var cinemaId = 1L;

        var createCinemaDto1 = CreateUpdateCinemaRoomDto.builder().name("test room")
                .cinemaId(cinemaId)
                .placeNumber(5)
                .rowsNum(6)
                .build();

        var createCinemaDto2 = CreateUpdateCinemaRoomDto.builder()
                .cinemaId(1L)
                .name("test room2")
                .placeNumber(8)
                .rowsNum(16)
                .build();

        var createCinemaRoomDtos = List.of(createCinemaDto1, createCinemaDto2);

        var cinemaRoomList = createCinemaRoomDtos.stream()
                .map(createCinemaRoomDto -> createCinemaRoomDto.toCinemaRoom().toEntity())
                .toList();

        var getCinemaRoomsDto = createCinemaRoomDtos.stream()
                .map(cinemaRoom -> cinemaRoom.toCinemaRoom().withCinemaId(1L).toGetCinemaRoomDto())
                .toList();

        when(cinemaRoomEntityDao.saveAll(any()))
                .thenReturn(cinemaRoomList);

        assertThat(cinemasService.addCinemaRoomsToCinema(1L, createCinemaRoomDtos))
                .isEqualTo(getCinemaRoomsDto);

    }

    @Test
    @DisplayName("when all cinemas from city are searched, should return 2 cinemas")
    public void test7() {

        var searchedCity = "Salzburg";

        var city1 = "Salzburg";
        var street1 = "Main Street";
        var houseNumber1 = "130";
        var zipCode1 = "34-456";
        var name1 = "Nice cinema";

        var cinema1 = CinemaInfo.builder()
                .name(name1)
                .city(city1)
                .street(street1)
                .houseNumber(houseNumber1)
                .zipCode(zipCode1)
                .build();

        var getCinemaInfoDto1 = GetCinemaInfoDto.builder()
                .name(name1)
                .city(city1)
                .street(street1)
                .houseNumber(houseNumber1)
                .zipCode(zipCode1)
                .build();

        var cinema2 = CinemaInfo.builder()
                .name(name1)
                .city(city1)
                .street(street1)
                .houseNumber(houseNumber1)
                .zipCode(zipCode1)
                .build();

        var getCinemaInfoDto2 = GetCinemaInfoDto.builder()
                .name(name1)
                .city(city1)
                .street(street1)
                .houseNumber(houseNumber1)
                .zipCode(zipCode1)
                .build();

        when(cinemaInfoDao.findByCity(searchedCity))
                .thenReturn(List.of(cinema1,cinema2));

        assertThat(cinemasService.findByCity(searchedCity))
                .isNotEmpty()
                .hasSize(2)
                .containsAll(List.of(getCinemaInfoDto1,getCinemaInfoDto2));
    }

    @Test
    @DisplayName("when searched city name has incorrect format")
    public void test8() {

        var cityName = "blp&-.@%";

        assertThatThrownBy(() -> cinemasService.findByCity(cityName))
                .isInstanceOf(CinemaServiceException.class)
                .hasMessageContaining("city have wrong format");

    }

    @Test
    @DisplayName("when searched city name is null")
    public void test9() {

        assertThatThrownBy(() -> cinemasService.findByCity(null))
                .isInstanceOf(CinemaServiceException.class)
                .hasMessageContaining("city is null");

    }

    @Test
    @DisplayName("when searching by name is succesfull")
    public void test10() {

        var city = "Salzburg";
        var street = "Main Street";
        var houseNumber = "130";
        var zipCode = "34-456";
        var name = "Nice cinema";

        var cinema = CinemaInfo.builder()
                .name(name)
                .city(city)
                .street(street)
                .houseNumber(houseNumber)
                .zipCode(zipCode)
                .build();

        var getCinemaInfoDto = GetCinemaInfoDto.builder()
                .name(name)
                .city(city)
                .street(street)
                .houseNumber(houseNumber)
                .zipCode(zipCode)
                .build();


        when(cinemaInfoDao.findByName(name))
                .thenReturn(Optional.of(cinema));

        assertThat(cinemasService.findByName(name))
                .isEqualTo(getCinemaInfoDto);

    }
}
