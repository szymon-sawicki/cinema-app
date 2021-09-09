package com.cinema.app.domain.ticket;

import com.cinema.app.domain.ticket.dto.GetTicketDto;
import com.cinema.app.domain.ticket.type.Status;
import com.cinema.app.domain.user.dto.CreateUpdateUserDto;
import com.cinema.app.infrastructure.persistence.entity.TicketEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class TicketDomainTest {

    @Test
    @DisplayName("when conversion to get ticket dto is correct")
            public void test1() {

        var price = new BigDecimal("25");
        var discount = 20;
        var screeningId = 4L;
        var seatId = 5L;
        var status = Status.CONFIRMED;
        var userId = 3L;
        var createUserDto = CreateUpdateUserDto.builder().build();

        var ticket = Ticket.builder()
                .price(price)
                .discount(discount)
                .screeningId(screeningId)
                .status(status)
                .build();

        var expectedTicketDto = GetTicketDto.builder()
                .price(price)
                .discount(discount)
                .screeningId(screeningId)
                .status(status)
                .build();


        assertThat(ticket.toGetTicketDto())
                .isEqualTo(expectedTicketDto);

    }


    @Test
    @DisplayName("when new seat id is given")
    public void test2() {

        var price = new BigDecimal("25");
        var discount = 20;
        var screeningId = 4L;
        var seatId = 5L;
        var newSeatId = 3L;
        var status = Status.CONFIRMED;
        var userId = 3L;
        var createUserDto = CreateUpdateUserDto.builder().build();

        var createTicketDto = Ticket.builder()
                .price(price)
                .discount(discount)
                .screeningId(screeningId)
                .status(status)
                .seatId(seatId)
                .userId(userId)
                .build();

        var expectedTicket = Ticket.builder()
                .price(price)
                .discount(discount)
                .screeningId(screeningId)
                .status(status)
                .seatId(newSeatId)
                .userId(userId)
                .build();

        assertThat(createTicketDto.withSeatId(newSeatId))
                .isEqualTo(expectedTicket);

    }

    @Test
    @DisplayName("when new user id is given")
    public void test3() {

        var price = new BigDecimal("25");
        var discount = 20;
        var screeningId = 4L;
        var seatId = 5L;
        var status = Status.CONFIRMED;
        var userId = 3L;
        var newUserId=7L;
        var createUserDto = CreateUpdateUserDto.builder().build();

        var createTicketDto = Ticket.builder()
                .price(price)
                .discount(discount)
                .screeningId(screeningId)
                .status(status)
                .seatId(seatId)
                .userId(userId)
                .build();

        var expectedTicket = Ticket.builder()
                .price(price)
                .discount(discount)
                .screeningId(screeningId)
                .status(status)
                .seatId(seatId)
                .userId(newUserId)
                .build();

        assertThat(createTicketDto.withUserId(newUserId))
                .isEqualTo(expectedTicket);

    }

    @Test
    @DisplayName("when conversion to entity is correct")
    public void test4() {

        var price = new BigDecimal("25");
        var discount = 20;
        var screeningId = 4L;
        var seatId = 5L;
        var status = Status.CONFIRMED;
        var userId = 3L;

        var ticket = Ticket.builder()
                .price(price)
                .discount(discount)
                .screeningId(screeningId)
                .status(status)
                .seatId(seatId)
                .userId(userId)
                .build();

        var expectedTicketEntity = TicketEntity.builder()
                .price(price)
                .discount(discount)
                .screeningId(screeningId)
                .status(status)
                .seatId(seatId)
                .userId(userId)
                .build();

        assertThat(ticket.toEntity())
                .isEqualTo(expectedTicketEntity);


    }
}
