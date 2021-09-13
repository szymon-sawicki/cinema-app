package com.cinema.app.application.service;

import com.cinema.app.domain.screening.dto.GetScreeningDto;
import com.cinema.app.domain.seat.dto.GetSeatDto;
import com.cinema.app.domain.ticket.Ticket;
import com.cinema.app.domain.ticket.dto.CreateTicketDto;
import com.cinema.app.domain.ticket.dto.GetTicketDto;
import com.cinema.app.domain.ticket.type.Status;
import com.cinema.app.domain.user.dto.CreateUpdateUserDto;
import com.cinema.app.domain.user.type.Gender;
import com.cinema.app.domain.user.type.Role;
import com.cinema.app.infrastructure.persistence.dao.ScreeningEntityDao;
import com.cinema.app.infrastructure.persistence.dao.SeatEntityDao;
import com.cinema.app.infrastructure.persistence.dao.TicketEntityDao;
import com.cinema.app.infrastructure.persistence.dao.UserEntityDao;
import com.cinema.app.infrastructure.persistence.entity.ScreeningEntity;
import com.cinema.app.infrastructure.persistence.entity.SeatEntity;
import com.cinema.app.infrastructure.persistence.entity.TicketEntity;
import com.cinema.app.infrastructure.persistence.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)


public class TicketsServiceTest {

    @Mock
    private TicketEntityDao ticketEntityDao;
    @Mock
    private UserEntityDao userEntityDao;
    @Mock
    private ScreeningEntityDao screeningEntityDao;
    @Mock
    private SeatEntityDao seatEntityDao;

    @InjectMocks
    private TicketsService ticketsService;

    @Test
    @DisplayName("when list with seats of screening is returned")
    public void test1() {

        var screeningId = 2L;

        var getScreeningDto = GetScreeningDto.builder()
                .id(screeningId)
                .cinemaRoomId(1L)
                .build();

        var seat1 = SeatEntity.builder()
                .id(1L)
                .cinemaRoomId(1L)
                .place(1)
                .rowNum(1)
                .build();

        var seat2 = SeatEntity.builder()
                .id(2L)
                .cinemaRoomId(1L)
                .place(2)
                .rowNum(1)
                .build();

        var seat3 = SeatEntity.builder()
                .id(3L)
                .cinemaRoomId(1L)
                .place(3)
                .rowNum(1)
                .build();


        var ticket1 = TicketEntity.builder()
                .seatId(1L)
                .screeningId(screeningId)
                .build();

        var ticket2 = TicketEntity.builder()
                .seatId(3L)
                .screeningId(screeningId)
                .build();

        when(seatEntityDao.findSeatsByCinemaRoom(1L))
                .thenReturn(List.of(seat1, seat2, seat3));
        when(ticketEntityDao.findByScreeningAndSeat(screeningId, 1L))
                .thenReturn(Optional.of(ticket1));
        when(ticketEntityDao.findByScreeningAndSeat(screeningId, 3L))
                .thenReturn(Optional.of(ticket2));


        var expectedHashMap = new HashMap<Long, Boolean>();
        expectedHashMap.put(1L, true);
        expectedHashMap.put(2L, false);
        expectedHashMap.put(3L, true);

        assertThat(ticketsService.mapSeatsOfScreening(screeningId))
                .hasSize(3)
                .containsAllEntriesOf(expectedHashMap);

    }

    @Test
    @DisplayName("when ticket is deleted")
    public void test2() {

        var ticketId = 2L;
        var seatId = 1L;
        var screeningId = 3L;


        var ticket = Ticket.builder()
                .seatId(seatId)
                .screeningId(screeningId)
                .build();

        var getTicketDto = GetTicketDto.builder()
                .seatId(seatId)
                .screeningId(screeningId)
                .build();

        when(ticketEntityDao.deleteById(ticketId))
                .thenReturn(Optional.of(ticket.toEntity()));

        assertThat(ticketsService.deleteTicket(ticketId))
                .isEqualTo(getTicketDto);

    }

    @Test
    @DisplayName("when status of ticket is updated")
    public void test3() {

        var ticketId = 2L;
        var seatId = 1L;
        var screeningId = 3L;
        var status = Status.CONFIRMED;
        var newStatus = Status.UNCONFIRMED;


        var ticket = TicketEntity.builder()
                .seatId(seatId)
                .screeningId(screeningId)
                .status(status)
                .build();

        var ticketWithUpdatedStatus = TicketEntity.builder()
                .seatId(seatId)
                .screeningId(screeningId)
                .status(newStatus)
                .build();

        var getTicketDto = GetTicketDto.builder()
                .seatId(seatId)
                .screeningId(screeningId)
                .status(newStatus)
                .build();

        when(ticketEntityDao.findById(ticketId))
                .thenReturn(Optional.of(ticket));

        when(ticketEntityDao.update(ticketId, ticketWithUpdatedStatus))
                .thenReturn(Optional.of(ticketWithUpdatedStatus));

        assertThat(ticketsService.updateTicketsStatus(ticketId, newStatus))
                .isEqualTo(getTicketDto);

    }

    @Test
    @DisplayName("when new tickets are created")
    public void test4() {

        var username = "Alfred";
        var mail = "alfred@alfredo.pl";
        var password = "123456";
        var name = "Alfredo Bueno";
        var role = Role.USER;
        var birtDate = LocalDate.now().minusYears(30);
        var gender = Gender.MALE;

        var screeningId = 4L;


        var user = CreateUpdateUserDto.builder()
                .username(username)
                .password(password)
                .mail(mail)
                .name(name)
                .role(role)
                .birthDate(birtDate)
                .gender(gender)
                .build();

        var userEntity = user.toUser().toEntity();

        var screening = ScreeningEntity.builder()
                .id(screeningId)
                .cinemaRoomId(1L)
                .build();

        var seat1 = SeatEntity.builder()
                .id(1L)
                .cinemaRoomId(1L)
                .place(1)
                .rowNum(1)
                .build();

        var seat2 = SeatEntity.builder()
                .id(2L)
                .cinemaRoomId(1L)
                .place(2)
                .rowNum(1)
                .build();

        var seat3 = SeatEntity.builder()
                .id(3L)
                .cinemaRoomId(1L)
                .place(3)
                .rowNum(1)
                .build();

        var getSeatDto1 = seat1.toSeat().toGetSeatDto();
        var getSeatDto2 = seat3.toSeat().toGetSeatDto();

        var ticketToInsert  = CreateTicketDto.builder()
                .createUserDto(user)
                .seats(List.of(getSeatDto1,getSeatDto2))
                .discount(0)
                .price(new BigDecimal("45"))
                .status(Status.CONFIRMED)
                .screeningId(screeningId)
                .build();


        var ticket1 = TicketEntity.builder()
                .seatId(1L)
                .screeningId(screeningId)
                .build();

        var ticket2 = TicketEntity.builder()
                .seatId(3L)
                .screeningId(screeningId)
                .build();

        var ticketsToInsert = List.of(ticket1,ticket2);

        var expectedTicket1  = GetTicketDto.builder()
                .seatId(1L)
                .discount(0)
                .price(new BigDecimal("45"))
                .status(Status.CONFIRMED)
                .screeningId(screeningId)
                .build();

        var expectedTicket2  = GetTicketDto.builder()
                .seatId(1L)
                .discount(0)
                .price(new BigDecimal("45"))
                .status(Status.CONFIRMED)
                .screeningId(screeningId)
                .build();

        var expectedList = List.of(expectedTicket1,expectedTicket2);


        when(seatEntityDao.findSeatsByCinemaRoom(1L))
                .thenReturn(List.of(seat1, seat2, seat3));

        when(userEntityDao.findByUsername(any()))
                .thenReturn(Optional.of(userEntity));

        when(screeningEntityDao.findById(screeningId))
                .thenReturn(Optional.of(screening));

        when(ticketEntityDao.saveAll(ticketsToInsert))
                .thenReturn(ticketsToInsert);



    }


}
