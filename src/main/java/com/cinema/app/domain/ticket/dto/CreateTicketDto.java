package com.cinema.app.domain.ticket.dto;

import com.cinema.app.domain.ticket.Ticket;
import com.cinema.app.domain.ticket.type.Status;
import com.cinema.app.domain.user.dto.CreateUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CreateTicketDto {

    // TODO dac screeningId/seatId, czy dto jako pole skladowe ?

    Long screeningId;
    Long seatId;
    CreateUserDto createUserDto;
    BigDecimal price;
    Integer discount;
    Status status;

    public Ticket toTicket() {
        return Ticket.builder()
                .screeningId(screeningId)
                .seatId(seatId)
                .price(price)
                .discount(discount)
                .status(status)
                .build();
    }

    public Ticket withUserId(Long newUserId) {
        return Ticket.builder()
                .screeningId(screeningId)
                .seatId(seatId)
                .userId(newUserId)
                .price(price)
                .discount(discount)
                .status(status)
                .build();
    }

}
