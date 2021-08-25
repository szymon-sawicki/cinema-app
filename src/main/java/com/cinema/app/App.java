package com.cinema.app;

import com.cinema.app.application.service.CinemasService;
import com.cinema.app.domain.address.Address;
import com.cinema.app.domain.address.dto.CreateAddressDto;
import com.cinema.app.domain.cinema.dto.CreateCinemaDto;
import com.cinema.app.domain.cinema_room.dto.CreateCinemaRoomDto;
import com.cinema.app.domain.movie.Movie;
import com.cinema.app.domain.movie.type.MovieGenre;
import com.cinema.app.domain.seat.Seat;
import com.cinema.app.domain.seat.type.SeatType;
import com.cinema.app.infrastructure.configs.AppSpringConfig;
import com.cinema.app.infrastructure.persistence.impl.AddressDaoImpl;
import com.cinema.app.infrastructure.persistence.impl.CinemaRoomDaoImpl;
import com.cinema.app.infrastructure.persistence.impl.MovieDaoImpl;
import com.cinema.app.infrastructure.persistence.impl.SeatDaoImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class App {
    public static void main(String[] args) {


        ApplicationContext context = new AnnotationConfigApplicationContext(AppSpringConfig.class);

       var addressDao = context.getBean("addressDaoImpl", AddressDaoImpl.class);
        var seatDao = context.getBean("seatDaoImpl", SeatDaoImpl.class);
        var cinemaService = context.getBean("cinemasService", CinemasService.class);
        var cinemaRoomDao = context.getBean("cinemaRoomDaoImpl", CinemaRoomDaoImpl.class);
        var movieDao = context.getBean("movieDaoImpl", MovieDaoImpl.class);

        var address = Address.builder()
                .city("Gniezno")
                .houseNumber("26A")
                .street("ola la la")
                .zipCode("62-200")
                .build();



        var address2 = Address.builder()
                .city("Poznan")
                .houseNumber("45")
                .zipCode("41-920")
                .build();

        var cinemaRoom = CreateCinemaRoomDto.builder()
                .cinemaId(1L)
                .rowsNum(5)
                .placeNumber(10)
                .name("mmmm")
                .build();

        var cinemaRoom2 = CreateCinemaRoomDto.builder()
                .cinemaId(1L)
                .rowsNum(8)
                .placeNumber(18)
                .name("oooo")
                .build();

        var cinemaRooms = List.of(cinemaRoom, cinemaRoom2);

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

       var movie = Movie.builder()
               .movieGenre(MovieGenre.ACTION)
               .length(30)
               .premiereDate(LocalDate.of(2020,11,23))
               .name("Indiana Jones")
               .build();

        System.out.println(movieDao.save(movie));
        System.out.println(movieDao.findByName("Bourne Ultimatum"));

     /*   System.out.println(addressDao.save(address));
        System.out.println(addressDao.save(address2));*/
/*        System.out.println(cinemaService.addCinema(createCinemaDto));
        System.out.println(seatDao.save(seat));*/

    //    System.out.println(cinemaService.findByName("fajne"));

        // TODO wyszukiwanie wszystkich kin z miasta za pomoca dao ? Trzeba zrobic join i specjalny obiekt?
        // TODO do poprawnego działania abstract crud dao potrzebne sa settery w klasie mapowanej, jak to ogarnac ? entity?
        // TODO jak najlepiej przechować datę i godzinę seansu w bazie danych i obiekcie ?
        // TODO struktura kontrolerów w warstwie sieciowej
        // TODO testowanie dodawania kina w cinemas service - sprawdzić
        // TODO

    }

}
