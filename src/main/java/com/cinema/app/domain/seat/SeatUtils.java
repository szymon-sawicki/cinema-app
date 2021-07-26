package com.cinema.app.domain.seat;

import java.util.function.Function;


/**
 * utility class used to achieve encapsulation in Seat objects
 * @author Szymon Sawicki
 */

public interface SeatUtils {

    Function<Seat,Long> toId = seat -> seat.id;

}
