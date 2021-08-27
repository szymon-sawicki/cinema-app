package com.cinema.app;

import com.cinema.app.application.service.CinemasService;
import com.cinema.app.application.service.MoviesService;
import com.cinema.app.application.service.ScreeningsService;
import com.cinema.app.domain.address.Address;
import com.cinema.app.domain.address.dto.CreateAddressDto;
import com.cinema.app.domain.cinema.dto.CreateCinemaDto;
import com.cinema.app.domain.cinema_room.dto.CreateCinemaRoomDto;
import com.cinema.app.domain.movie.Movie;
import com.cinema.app.domain.movie.dto.CreateMovieDto;
import com.cinema.app.domain.movie.type.MovieGenre;
import com.cinema.app.domain.screening.Screening;
import com.cinema.app.domain.screening.dto.CreateScreeningDto;
import com.cinema.app.domain.seat.Seat;
import com.cinema.app.domain.seat.type.SeatType;
import com.cinema.app.infrastructure.configs.AppSpringConfig;
import com.cinema.app.infrastructure.persistence.ScreeningDao;
import com.cinema.app.infrastructure.persistence.impl.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class App {
    public static void main(String[] args) {


        ApplicationContext context = new AnnotationConfigApplicationContext(AppSpringConfig.class);

        var addressDao = context.getBean("addressDaoImpl", AddressDaoImpl.class);
        var seatDao = context.getBean("seatDaoImpl", SeatDaoImpl.class);
        var cinemaService = context.getBean("cinemasService", CinemasService.class);
        var cinemaRoomDao = context.getBean("cinemaRoomDaoImpl", CinemaRoomDaoImpl.class);
        var movieDao = context.getBean("movieDaoImpl", MovieDaoImpl.class);
        var moviesService = context.getBean("moviesService", MoviesService.class);
        var screeningDao = context.getBean("screeningDaoImpl", ScreeningDaoImpl.class);
        var screeningsService = context.getBean("screeningsService", ScreeningsService.class);

        var address = Address.builder()
                .city("Gniezno")
                .houseNumber("26A")
                .street("ola la la")
                .zipCode("62-200")
                .build();

        var createAddressDto2 = CreateAddressDto.builder()
                .city("Ponan")
                .houseNumber("26A")
                .street("vfvbf")
                .zipCode("89-22")
                .build();


        var address2 = Address.builder()
                .city("Poznan")
                .houseNumber("45")
                .zipCode("41-920")
                .build();

        var cinemaRoom = CreateCinemaRoomDto.builder()
                .rowsNum(5)
                .placeNumber(10)
                .name("mmmm")
                .build();

        var cinemaRoom2 = CreateCinemaRoomDto.builder()
                .rowsNum(8)
                .placeNumber(18)
                .name("oooo")
                .build();

        var cinemaRoom3 = CreateCinemaRoomDto.builder()
                .rowsNum(5)
                .placeNumber(10)
                .name("room45")
                .build();

        var cinemaRoom4 = CreateCinemaRoomDto.builder()
                .rowsNum(8)
                .placeNumber(18)
                .name("room55")
                .build();

        var cinemaRooms = List.of(cinemaRoom, cinemaRoom2);
        var cinemaRooms2 = List.of(cinemaRoom3, cinemaRoom4);


        var createAddressDto = CreateAddressDto.builder()
                .city("Poz")
                .street("Main street")
                .houseNumber("25")
                .zipCode("62-200")
                .build();

        var createCinemaDto = CreateCinemaDto.builder()
                .name("fajne")
                .createAddressDto(createAddressDto)
                .cinemaRoomDtos(cinemaRooms)
                .build();


        var createCinemaDto2 = CreateCinemaDto.builder()
                .name("ekstra")
                .createAddressDto(createAddressDto2)
                .cinemaRoomDtos(cinemaRooms2)
                .build();

     System.out.println(cinemaService.addCinema(createCinemaDto));
      //  System.out.println(cinemaService.addCinema(createCinemaDto2));

        var rowNum = 2;
        var place = 3;
        var seatType = SeatType.SOFA;
        var cinemaRoomId = 1L;

        var seat = Seat.builder()
                .rowNum(rowNum)
                .place(place)
                .seatType(seatType)
                .cinemaRoomId(cinemaRoomId)
                .build();

        var movie = CreateMovieDto.builder()
                .movieGenre(MovieGenre.HORROR)
                .length(40)
                .premiereDate(LocalDate.of(2020, 11, 23))
                .name("Andreas Abenteuerrrr")
                .build();

        var movie2 = CreateMovieDto.builder()
                .movieGenre(MovieGenre.HORROR)
                .length(75)
                .premiereDate(LocalDate.of(2018, 11, 23))
                .name("Zombie123")
                .build();

/*        var createScreeningDto = CreateScreeningDto.builder()
                .getMovieDto(movieDao.findById(3L).orElseThrow().toGetMovieDto())
                .cinemaRoomId(3L)
                .time(LocalTime.now().plusHours(5))
                .date(LocalDate.now().plusDays(3))
                .build();*/

        var screening = Screening.builder()
                .cinemaRoomId(2L)
                .movieId(2L)
                .dateTime(LocalDateTime.now().plusDays(20))
                .build();



      System.out.println(screeningDao.save(screening));


        System.out.println();

       // System.out.println(screeningDao.save(screening));

        //     System.out.println(movieDao.findByName("Bourne Ultimatum"));

     /*   System.out.println(addressDao.save(address));
        System.out.println(addressDao.save(address2));*/
/*        System.out.println(cinemaService.addCinema(createCinemaDto));
        System.out.println(seatDao.save(seat));*/

        //    System.out.println(cinemaService.findByName("fajne"));

        // TODO do poprawnego działania abstract crud dao potrzebne sa settery w klasie mapowanej, jak to ogarnac ? entity?
        // TODO testowanie dodawania kina w cinemas service - sprawdzić
        // TODO

    }

}
