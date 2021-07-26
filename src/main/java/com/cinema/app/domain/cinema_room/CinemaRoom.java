package com.cinema.app.domain.cinema_room;

import com.cinema.app.domain.cinema.Cinema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode

/**
 * class representing one room of the cinema
 * @author Szymon Sawicki
 */

public class CinemaRoom {

    Long id;
    String name;
    Integer rows;
    Integer placeNumber;
    Cinema cinema;

}
