package com.cinema.app.domain.cinema_room;

import java.util.function.Function;


/**
 * utility class used to achieve encapsulation in CinemaRoom objects
 * @author Szymon Sawicki
 */

public interface CinemaRoomUtils {

    Function<CinemaRoom,Long> toId = cinemaRoom -> cinemaRoom.id;

}
