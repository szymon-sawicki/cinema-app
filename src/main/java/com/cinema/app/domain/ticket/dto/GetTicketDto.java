package com.cinema.app.domain.ticket.dto;


import com.cinema.app.domain.ticket.type.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GetTicketDto {

    Long id;
    Long screeningId;
    Long userId;
    Long seatId;
    BigDecimal price;
    Integer discount;
    Status status;

}
