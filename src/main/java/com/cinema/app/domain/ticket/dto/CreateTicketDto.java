package com.cinema.app.domain.ticket.dto;

import com.cinema.app.domain.seat.Seat;
import com.cinema.app.domain.seat.dto.GetSeatDto;
import com.cinema.app.domain.ticket.Ticket;
import com.cinema.app.domain.ticket.type.Status;
import com.cinema.app.domain.user.dto.CreateUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CreateTicketDto {


    Long screeningId;
    List<GetSeatDto> seats;
    CreateUserDto createUserDto;
    BigDecimal price;
    Integer discount;
    Status status;

    public Ticket toTicket() {
        return Ticket.builder()
                .screeningId(screeningId)
                .price(price)
                .discount(discount)
                .status(status)
                .build();
    }

}
