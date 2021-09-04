package com.cinema.app;

import com.cinema.app.application.service.CinemasService;
import com.cinema.app.application.service.MoviesService;
import com.cinema.app.application.service.ScreeningsService;
import com.cinema.app.application.service.TicketsService;
import com.cinema.app.domain.address.dto.CreateAddressDto;
import com.cinema.app.domain.cinema.dto.CreateCinemaDto;
import com.cinema.app.domain.cinema_room.dto.CreateCinemaRoomDto;
import com.cinema.app.domain.movie.dto.CreateMovieDto;
import com.cinema.app.domain.movie.type.MovieGenre;
import com.cinema.app.domain.screening.dto.CreateScreeningDto;
import com.cinema.app.domain.screening.dto.GetScreeningDto;
import com.cinema.app.domain.seat.dto.GetSeatDto;
import com.cinema.app.domain.ticket.Ticket;
import com.cinema.app.domain.ticket.dto.CreateTicketDto;
import com.cinema.app.domain.ticket.type.Status;
import com.cinema.app.domain.user.User;
import com.cinema.app.domain.user.dto.CreateUserDto;
import com.cinema.app.domain.user.type.Gender;
import com.cinema.app.infrastructure.configs.AppSpringConfig;
import com.cinema.app.infrastructure.persistence.entity.ScreeningEntity;
import com.cinema.app.infrastructure.persistence.entity.TicketEntity;
import com.cinema.app.infrastructure.persistence.entity.UserEntity;
import com.cinema.app.infrastructure.persistence.impl.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class App {
    public static void main(String[] args) {


        ApplicationContext context = new AnnotationConfigApplicationContext(AppSpringConfig.class);


        var cinemaService = context.getBean("cinemasService", CinemasService.class);
        var moviesService = context.getBean("moviesService", MoviesService.class);
        var screeningsService = context.getBean("screeningsService", ScreeningsService.class);
        var screeningInfoDao = context.getBean("screeningInfoDaoImpl",ScreeningInfoDaoImpl.class);
        var screeningDao = context.getBean("screeningEntityDaoImpl",ScreeningEntityDaoImpl.class);
        var ticketsService = context.getBean("ticketsService", TicketsService.class);

        // SAMPLE DATA TO TEST

        var address1 = CreateAddressDto.builder()
                .city("Zurich")
                .houseNumber("234")
                .street("Haupt Strasse")
                .zipCode("62-200")
                .build();

        var address2 = CreateAddressDto.builder()
                .city("Berlin")
                .houseNumber("123/5")
                .street("Lange Gasse")
                .zipCode("89-226")
                .build();


        var address3 = CreateAddressDto.builder()
                .city("Wien")
                .street("Donaufelder Strasse 28")
                .houseNumber("156")
                .zipCode("41-920")
                .build();

        var cinemaRoom1 = CreateCinemaRoomDto.builder()
                .rowsNum(15)
                .placeNumber(10)
                .name("big cinema room")
                .build();

        var cinemaRoom2 = CreateCinemaRoomDto.builder()
                .rowsNum(12)
                .placeNumber(8)
                .name("middle cinema room")
                .build();

        var cinemaRoom3 = CreateCinemaRoomDto.builder()
                .rowsNum(5)
                .placeNumber(10)
                .name("small  cinema room")
                .build();

        var cinemaRoom4 = CreateCinemaRoomDto.builder()
                .rowsNum(4)
                .placeNumber(5)
                .name("vip cinema room")
                .build();

        var cinemaRooms1 = List.of(cinemaRoom1, cinemaRoom2, cinemaRoom4);
        var cinemaRooms2 = List.of(cinemaRoom3, cinemaRoom4, cinemaRoom2);
        var cinemaRooms3 = List.of(cinemaRoom1, cinemaRoom3, cinemaRoom3);

        var createCinemaDto1 = CreateCinemaDto.builder()
                .name("Great Cinema od Zurich")
                .createAddressDto(address1)
                .cinemaRoomDtos(cinemaRooms1)
                .build();

        var createCinemaDto2 = CreateCinemaDto.builder()
                .name("Berliner Cinema")
                .createAddressDto(address2)
                .cinemaRoomDtos(cinemaRooms2)
                .build();

        var createCinemaDto3 = CreateCinemaDto.builder()
                .name("Vienna City Cinema")
                .createAddressDto(address3)
                .cinemaRoomDtos(cinemaRooms3)
                .build();

        var movie = CreateMovieDto.builder()
                .movieGenre(MovieGenre.HORROR)
                .length(90)
                .premiereDate(LocalDate.of(2020, 11, 23))
                .title("Andreas Abenteuer")
                .build();

        var movie2 = CreateMovieDto.builder()
                .movieGenre(MovieGenre.HORROR)
                .length(110)
                .premiereDate(LocalDate.of(2018, 11, 23))
                .title("Zombie 12")
                .build();

        var ticketEntity = TicketEntity.builder()
                .price(new BigDecimal("250"))
                .discount(20)
                .screeningId(1L)
                .userId(1L)
                .seatId(1L)
                .status(Status.CONFIRMED)
                .build();



        var user1 = CreateUserDto.builder()
                .name("Andreas")
                .gender(Gender.MALE)
                .birthDate(LocalDate.now().minusYears(30))
                .mail("andi@andi.com")
                .username("andielo")
                .password("123467899")
                .build();

        var screening1 = CreateScreeningDto.builder()
                .cinemaRoomId(3L)
                .createMovieDto(CreateMovieDto.builder()
                        .title("Andreas Abenteuer")
                        .length(66)
                        .premiereDate(LocalDate.of(2020,5,5))
                        .movieGenre(MovieGenre.ACTION)
                        .build())
                .dateTime(LocalDateTime.of(2021,10,1,21,30))
                .build();

        var screening2 = CreateScreeningDto.builder()
                .cinemaRoomId(3L)
                .createMovieDto(movie)
                .dateTime(LocalDateTime.of(2021,10,1,18,30))
                .build();

        var screening3 = CreateScreeningDto.builder()
                .cinemaRoomId(3L)
                .createMovieDto(movie2)
                .dateTime(LocalDateTime.of(2021,10,1,15,0))
                .build();

        var getScreeningDto = GetScreeningDto.builder()
                .id(1L)
                .cinemaRoomId(3L)
                .movieId(1L)
                .dateTime(LocalDateTime.of(2021,10,1,21,30))
                .build();

        var getSeatDto1 = GetSeatDto.builder()
                .cinemaRoomId(3L)
                .id(13L)
                .build();

        var getSeatDto2 = GetSeatDto.builder()
                .cinemaRoomId(3L)
                .id(14L)
                .build();

        var getSeatDto3 = GetSeatDto.builder()
                .cinemaRoomId(3L)
                .id(15L)
                .build();

        var ticket1 = CreateTicketDto.builder()
                .createUserDto(user1)
                .discount(0)
                .screeningId(2L)
                .seats(List.of(getSeatDto3,getSeatDto2,getSeatDto1))
                .status(Status.CONFIRMED)
                .price(new BigDecimal("25"))
                .build();

        var ticket2 = CreateTicketDto.builder()
                .createUserDto(user1)
                .discount(0)
                .screeningId(2L)
                .seats(List.of())
                .status(Status.CONFIRMED)
                .price(new BigDecimal("25"))
                .build();

        var ticket3 = CreateTicketDto.builder()
                .createUserDto(user1)
                .discount(0)
                .screeningId(2L)
                .seats(List.of())
                .status(Status.CONFIRMED)
                .price(new BigDecimal("25"))
                .build();

        System.out.println(ticketsService.createTickets(ticket1));

/*        // creating cinemas (with rooms and seats)
        System.out.println(cinemaService.addCinema(createCinemaDto1));
        System.out.println(cinemaService.addCinema(createCinemaDto2));
        System.out.println(cinemaService.addCinema(createCinemaDto3));
        // creating screenings (with movies)
        System.out.println(screeningsService.createScreeening(screening1));
        System.out.println(screeningsService.createScreeening(screening3));
        System.out.println(screeningsService.createScreeening(screening2));*/
        // creating tickets (with users)
/*

        System.out.println(ticketsService.createTicket(ticket2));
        System.out.println(ticketsService.createTicket(ticket3));
        System.out.println(ticketsService.getSeatsOfScreening(screeningDao.findById(1L).orElseThrow().toScreening().toGetScreeningDto()));


*/

/*ticketsService.mapSeatsOfScreening(getScreeningDto)
        .entrySet()
        .stream()
        .forEach(entry -> System.out.println(entry.getKey().getId() + " : " + entry.getValue()));*/
   //     System.out.println(screeningInfoDao.findByKeyword("Zombie 12"));

    /*
        System.out.println(cinemaService.addCinema(createCinemaDto2));
        System.out.println(cinemaService.addCinema(createCinemaDto3));

        System.out.println(cinemaService.findByCity("Berlin"));*/
     /*
        System.out.println(moviesService.addMovie(movie));
        System.out.println(moviesService.addMovie(movie2));*/
    }

}
