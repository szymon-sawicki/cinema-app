package com.cinema.app.infrastructure.persistence;

import com.cinema.app.domain.seat.Seat;
import com.cinema.app.infrastructure.persistence.generic.CrudDao;


import java.util.List;

public interface SeatDao extends CrudDao<Seat,Long> {

    List<Seat> findAllSeatsFromRoom(Long roomId);

}
