package com.cinema.app.domain.ticket;

import com.cinema.app.domain.ticket.dto.GetTicketDto;
import com.cinema.app.domain.ticket.type.Status;
import com.cinema.app.infrastructure.persistence.entity.TicketEntity;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString

public class Ticket {

    Long id;
    Long screeningId;
    Long userId;
    Long seatId;
    BigDecimal price;
    Integer discount;
    Status status;

    public Ticket withUserId(Long newUserId) {
        return Ticket.builder()
                .id(id)
                .screeningId(screeningId)
                .userId(newUserId)
                .seatId(seatId)
                .price(price)
                .discount(discount)
                .status(status)
                .build();
    }


    public Ticket withSeatId(Long newSeatId) {
        return Ticket.builder()
                .id(id)
                .screeningId(screeningId)
                .userId(userId)
                .seatId(newSeatId)
                .price(price)
                .discount(discount)
                .status(status)
                .build();
    }

    public Ticket withStatus(Status newStatus) {
        return Ticket.builder()
                .id(id)
                .screeningId(screeningId)
                .userId(userId)
                .seatId(seatId)
                .price(price)
                .discount(discount)
                .status(newStatus)
                .build();
    }

    public GetTicketDto toGetTicketDto() {
        return GetTicketDto.builder()
                .id(id)
                .screeningId(screeningId)
                .userId(userId)
                .seatId(seatId)
                .price(price)
                .discount(discount)
                .status(status)
                .build();
    }

    public TicketEntity toEntity() {
        return TicketEntity.builder()
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
