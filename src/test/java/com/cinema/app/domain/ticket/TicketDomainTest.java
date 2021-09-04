package com.cinema.app.domain.ticket;

import com.cinema.app.domain.ticket.dto.CreateTicketDto;
import com.cinema.app.domain.ticket.type.Status;
import com.cinema.app.domain.user.dto.CreateUserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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
        var createUserDto = CreateUserDto.builder().build();

        var createTicketDto = CreateTicketDto.builder()
                .price(price)
                .discount(discount)
                .screeningId(screeningId)
                .status(status)
                .createUserDto(createUserDto)
                .build();

        var expectedTicket = Ticket.builder()
                .price(price)
                .discount(discount)
                .screeningId(screeningId)
                .status(status)
                .build();


        Assertions.assertThat(createTicketDto.toTicket())
                .isEqualTo(expectedTicket);


    }
}
