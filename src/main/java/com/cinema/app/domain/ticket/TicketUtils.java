package com.cinema.app.domain.ticket;

import java.util.function.Function;

public interface TicketUtils {
    Function<Ticket,Long> toId = ticket -> ticket.id;
}
