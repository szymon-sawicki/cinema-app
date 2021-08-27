package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.CinemaServiceException;
import com.cinema.app.domain.address.Address;
import com.cinema.app.domain.address.dto.CreateAddressDto;
import com.cinema.app.domain.cinema.Cinema;
import com.cinema.app.domain.cinema.dto.CreateCinemaDto;
import com.cinema.app.domain.cinema.dto.GetCinemaDto;
import com.cinema.app.domain.cinema_room.dto.CreateCinemaRoomDto;
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
import org.mockito.junit.MockitoJUnitRunner;



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
    private CinemaDao cinemaDao;

    @Mock
    private AddressDao addressDao;

    @Mock
    private CinemaRoomDao cinemaRoomDao;

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

        when(addressDao.findAllFromCity("berlin"))
                .thenReturn(List.of(
                        Address.builder().city("berlin").street("haupt").zipCode("67-200").houseNumber("234").build(),
                        Address.builder().city("berlin").street("kurzweg").zipCode("67-200").houseNumber("567").build()
                ));
*/
        var id = 3L;
        var name = "Gutkino";
        var addressId = 5L;

        when(cinemaDao.findByName("Gutkino"))
                .thenReturn(Optional.of(Cinema.builder()
                        .id(3L)
                        .name("Gutkino")
                        .addressId(5L)
                        .build()
                ));

        var expectedGetCinemaDto = GetCinemaDto.builder()
                .id(id)
                .name(name)
                .addressId(addressId)
                .build();

        assertThat(cinemasService.findByName("Gutkino"))
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

        var createAddressDto = CreateAddressDto.builder()
                .city(city)
                .street(street)
                .zipCode(zipCode)
                .houseNumber(houseNumber)
                .build();

        when(addressDao.findAddress(street, houseNumber, city, zipCode))
                .thenReturn(Optional.of(
                        Address.builder()
                                .id(addressId)
                                .city(city).street(street)
                                .zipCode(zipCode)
                                .houseNumber(houseNumber)
                                .build()
                ));

        var createCinemaRoomDto = CreateCinemaRoomDto.builder()
                .rowsNum(5)
                .placeNumber(10)
                .cinemaId(cinemaId)
                .name("mmmm")
                .build();

        var createCinemaRoomDto1 = CreateCinemaRoomDto.builder()
                .rowsNum(8)
                .placeNumber(18)
                .cinemaId(cinemaId)
                .name("oooo")
                .build();

        var createCinemaRoomDtos = List.of(createCinemaRoomDto, createCinemaRoomDto1);

        var cinemaRooms = createCinemaRoomDtos.stream()
                .map(CreateCinemaRoomDto::toCinemaRoom)
                .toList();

        when(cinemaRoomDao.saveAll(cinemaRooms))
                .thenReturn(cinemaRooms);


        var name = "Turbo Cinema";

        var createCinemaDto = CreateCinemaDto.builder()
                .name(name)
                .createAddressDto(createAddressDto)
                .cinemaRoomDtos(createCinemaRoomDtos)
                .build();

        var cinema = Cinema.builder()
                .id(cinemaId)
                .name(name)
                .addressId(addressId)
                .build();

        when(cinemaDao.save(any(Cinema.class)))
                .thenReturn(Optional.of(cinema));

        assertThat(cinemasService.addCinema(createCinemaDto))
                .isEqualTo(cinema.toGetCinemaDto());


    }

    @Test
    @DisplayName("when cinema id is null (by creating cinema rooms)")
    public void test3() {

        assertThatThrownBy(() -> cinemasService
                .addCinemaRoomsToCinema(null, List.of(CreateCinemaRoomDto.builder().build()
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

        var createCinemaDto1 = CreateCinemaRoomDto.builder().name("test room")
                .cinemaId(cinemaId)
                .placeNumber(5)
                .rowsNum(6)
                .build();

        var createCinemaDto2 = CreateCinemaRoomDto.builder()
                .cinemaId(1L)
                .name("test room2")
                .placeNumber(8)
                .rowsNum(16)
                .build();

        var createCinemaRoomDtos = List.of(createCinemaDto1, createCinemaDto2);

        var cinemaRoomList = createCinemaRoomDtos.stream()
                .map(CreateCinemaRoomDto::toCinemaRoom)
                .toList();

        var getCinemaRoomsDto = createCinemaRoomDtos.stream()
                .map(cinemaRoom -> cinemaRoom.toCinemaRoom().withCinemaId(1L).toGetCinemaRoomDto())
                .toList();

        when(cinemaRoomDao.saveAll(cinemaRoomList))
                .thenReturn(cinemaRoomList);

        assertThat(cinemasService.addCinemaRoomsToCinema(1L, createCinemaRoomDtos))
                .isEqualTo(getCinemaRoomsDto);

    }

    @Test
    @DisplayName("when all cinemas from city are searched, should return 2 cinemas")
    public void test7() {

        var searchedCity = "Salzburg";

        var address1 = Address.builder()
                .id(1L)
                .city("Salzburg")
                .street("Lange Strasse")
                .houseNumber("345/7")
                .build();

        var address2 = Address.builder()
                .id(2L)
                .city("Salzburg")
                .street("Strass")
                .houseNumber("3")
                .build();

        var address3 = Address.builder()
                .id(3L)
                .city("Wien")
                .street("Wagramer Strasse")
                .houseNumber("205")
                .build();

        var cinema1 = Cinema.builder()
                .name("Cineplexxx")
                .addressId(1L)
                .build();

        var cinema2 = Cinema.builder()
                .name("Cineplexxx2")
                .addressId(2L)
                .build();

        var cinema3 = Cinema.builder()
                .name("Cineplexxx3")
                .addressId(3L)
                .build();

        when(addressDao.findAllFromCity("Salzburg"))
                .thenReturn(List.of(address1, address2));

        when(cinemaDao.findByAddress(1L))
                .thenReturn(Optional.of(cinema1));

        when(cinemaDao.findByAddress(2L))
                .thenReturn(Optional.of(cinema2));

        when(cinemaDao.findByAddress(3L))
                .thenReturn(Optional.of(cinema3));

        assertThat(cinemasService.findByCity(searchedCity))
                .isNotEmpty()
                .hasSize(2)
                .containsAll(List.of(cinema1.toGetCinemaDto(), cinema2.toGetCinemaDto()));
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

        var id = 1L;
        var addressId = 2L;
        var name = "Nice cinema";

        var cinema = Cinema.builder()
                .id(id)
                .name(name)
                .addressId(addressId)
                .build();

        var getCinemaDto = GetCinemaDto.builder()
                .id(id)
                .name(name)
                .addressId(addressId)
                .build();


        when(cinemaDao.findByName(name))
                .thenReturn(Optional.of(cinema));

        assertThat(cinemasService.findByName(name))
                .isEqualTo(getCinemaDto);

    }
}
