package com.cinema.app.domain.cinema;

import java.util.function.Function;


/**
 * utility class used to achieve encapsulation in Cinema objects
 * @author Szymon Sawicki
 */

public interface CinemaUtils {

    Function<Cinema,Long> toId = cinema -> cinema.id;

}
