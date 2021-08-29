package com.cinema.app.domain.ticket.dto;

import com.cinema.app.domain.ticket.Ticket;
import com.cinema.app.domain.ticket.type.Status;
import com.cinema.app.domain.user.dto.GetUserDto;
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
    GetUserDto getUserDto;
    BigDecimal price;
    Integer discount;
    Status status;

    public Ticket toTicket() {
        return Ticket.builder()
                .screeningId(screeningId)
                .seatId(seatId)
                .userId(getUserDto.getId())
                .price(price)
                .discount(discount)
                .status(status)
                .build();

    }

}
