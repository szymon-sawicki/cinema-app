package com.cinema.app;

import com.cinema.app.application.service.*;
import com.cinema.app.domain.address.dto.CreateUpdateAddressDto;
import com.cinema.app.domain.cinema.dto.CreateUpdateCinemaDto;
import com.cinema.app.domain.cinema_room.dto.CreateUpdateCinemaRoomDto;
import com.cinema.app.domain.movie.dto.CreateUpdateMovieDto;
import com.cinema.app.domain.movie.type.MovieGenre;
import com.cinema.app.domain.screening.dto.CreateUpdateScreeningDto;
import com.cinema.app.domain.screening.dto.GetScreeningDto;
import com.cinema.app.domain.seat.dto.GetSeatDto;
import com.cinema.app.domain.ticket.dto.CreateTicketDto;
import com.cinema.app.domain.ticket.type.Status;
import com.cinema.app.domain.user.dto.CreateUpdateUserDto;
import com.cinema.app.domain.user.type.Gender;
import com.cinema.app.domain.user.type.Role;
import com.cinema.app.infrastructure.persistence.entity.TicketEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor

/**
 * class used to load sample test data into db
 * @Author Szymon Sawicki
 */

public class SampleDataLoader {

    private final CinemasService cinemasService;
    private final UsersService usersService;
    private final MoviesService moviesService;
    private final ScreeningsService screeningsService;
    private final TicketsService ticketsService;


    public String loadSampleData() {


        var address1 = CreateUpdateAddressDto.builder()
                .city("Zurich")
                .houseNumber("234")
                .street("Haupt Strasse")
                .zipCode("62-200")
                .build();

        var address2 = CreateUpdateAddressDto.builder()
                .city("Berlin")
                .houseNumber("123/5")
                .street("Lange Gasse")
                .zipCode("89-226")
                .build();


        var address3 = CreateUpdateAddressDto.builder()
                .city("Wien")
                .street("Donaufelder Strasse 28")
                .houseNumber("156")
                .zipCode("41-920")
                .build();

        var cinemaRoom1 = CreateUpdateCinemaRoomDto.builder()
                .rowsNum(15)
                .placeNumber(10)
                .name("big cinema room")
                .build();

        var cinemaRoom2 = CreateUpdateCinemaRoomDto.builder()
                .rowsNum(12)
                .placeNumber(8)
                .name("middle cinema room")
                .build();

        var cinemaRoom3 = CreateUpdateCinemaRoomDto.builder()
                .rowsNum(5)
                .placeNumber(10)
                .name("small  cinema room")
                .build();

        var cinemaRoom4 = CreateUpdateCinemaRoomDto.builder()
                .rowsNum(4)
                .placeNumber(5)
                .name("vip cinema room")
                .build();

        var cinemaRooms1 = List.of(cinemaRoom1, cinemaRoom2, cinemaRoom4);
        var cinemaRooms2 = List.of(cinemaRoom3, cinemaRoom4, cinemaRoom2);
        var cinemaRooms3 = List.of(cinemaRoom1, cinemaRoom3, cinemaRoom3);

        var createCinemaDto1 = CreateUpdateCinemaDto.builder()
                .name("Great Cinema od Zurich")
                .createAddressDto(address1)
                .cinemaRoomDtos(cinemaRooms1)
                .build();

        var createCinemaDto2 = CreateUpdateCinemaDto.builder()
                .name("Berliner Cinema")
                .createAddressDto(address2)
                .cinemaRoomDtos(cinemaRooms2)
                .build();

        var createCinemaDto3 = CreateUpdateCinemaDto.builder()
                .name("Vienna City Cinema")
                .createAddressDto(address3)
                .cinemaRoomDtos(cinemaRooms3)
                .build();

        var movie = CreateUpdateMovieDto.builder()
                .movieGenre(MovieGenre.HORROR)
                .length(90)
                .premiereDate(LocalDate.of(2020, 11, 23))
                .title("Andreas Abenteuer")
                .build();

        var movie2 = CreateUpdateMovieDto.builder()
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


        var user1 = CreateUpdateUserDto.builder()
                .name("Andreas Gross")
                .gender(Gender.MALE)
                .birthDate(LocalDate.now().minusYears(30).minusDays(50))
                .mail("andi@andi.com")
                .role(Role.USER)
                .username("andielo")
                .password("TurboAndi12.$")
                .passwordConfirmation("TurboAndi12.$")
                .build();

        var user2 = CreateUpdateUserDto.builder()
                .name("Michael Paddington")
                .gender(Gender.MALE)
                .role(Role.ADMIN)
                .birthDate(LocalDate.now().minusYears(30))
                .mail("michi@ole.com")
                .username("michi123")
                .password("Michael12.$")
                .passwordConfirmation("Michael12.$")
                .build();

        var screening1 = CreateUpdateScreeningDto.builder()
                .cinemaRoomId(3L)
                .createUpdateMovieDto(CreateUpdateMovieDto.builder()
                        .title("Andreas Abenteuer")
                        .length(66)
                        .premiereDate(LocalDate.of(2020, 5, 5))
                        .movieGenre(MovieGenre.ACTION)
                        .build())
                .dateTime(LocalDateTime.of(2021, 11, 1, 21, 30))
                .build();

        var screening2 = CreateUpdateScreeningDto.builder()
                .cinemaRoomId(3L)
                .createUpdateMovieDto(movie)
                .dateTime(LocalDateTime.of(2021, 10, 1, 18, 30))
                .build();

        var screening3 = CreateUpdateScreeningDto.builder()
                .cinemaRoomId(3L)
                .createUpdateMovieDto(movie2)
                .dateTime(LocalDateTime.of(2021, 10, 1, 15, 0))
                .build();

        var getScreeningDto = GetScreeningDto.builder()
                .id(1L)
                .cinemaRoomId(3L)
                .movieId(1L)
                .dateTime(LocalDateTime.of(2021, 10, 1, 21, 30))
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
                .screeningId(3L)
                .seats(List.of(getSeatDto3, getSeatDto2, getSeatDto1))
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

        StringBuilder result = new StringBuilder();

        cinemasService.addCinema(createCinemaDto1);
        cinemasService.addCinema(createCinemaDto2);
        cinemasService.addCinema(createCinemaDto3);

        result.append("Cinemas created. ");

        usersService.createUser(user1);
        usersService.createUser(user2);

        result.append("Users created. ");

        moviesService.addMovie(movie);
        moviesService.addMovie(movie2);

        result.append("Movies created. ");

        screeningsService.createScreeening(screening1);
        screeningsService.createScreeening(screening2);
        screeningsService.createScreeening(screening3);

        result.append("Screenings created. ");

        ticketsService.createTickets(ticket1);

        result.append("Ticket created");

        return result.append(" Whole sample data created successfully !").toString();

    }
}
