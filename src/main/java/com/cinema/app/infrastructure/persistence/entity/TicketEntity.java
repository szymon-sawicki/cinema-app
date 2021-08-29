package com.cinema.app.infrastructure.persistence.entity;

import com.cinema.app.domain.ticket.Ticket;
import com.cinema.app.domain.ticket.type.Status;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@EqualsAndHashCode

public class TicketEntity {

    Long id;
    Long screeningId;
    Long userId;
    Long seatId;
    BigDecimal price;
    Integer discount;
    Status status;

    public Ticket toTicket() {
        return Ticket.builder()
                .id(id)
                .screeningId(screeningId)
                .seatId(seatId)
                .userId(userId)
                .price(price)
                .discount(discount)
                .status(status)
                .build();
    }
}
