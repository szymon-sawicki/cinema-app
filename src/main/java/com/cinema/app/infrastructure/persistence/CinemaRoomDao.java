package com.cinema.app.infrastructure.persistence;

import com.cinema.app.domain.cinema.Cinema;
import com.cinema.app.domain.cinema_room.CinemaRoom;
import com.cinema.app.domain.seat.Seat;
import com.cinema.app.infrastructure.persistence.generic.CrudDao;

import java.util.List;

public interface CinemaRoomDao extends CrudDao<CinemaRoom, Long> {

    List<CinemaRoom> findAllRoomsFromCinema(Long cinemaId);

}
