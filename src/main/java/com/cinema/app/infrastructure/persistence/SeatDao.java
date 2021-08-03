package com.cinema.app.infrastructure.persistence;

import com.cinema.app.domain.seat.Seat;
import com.cinema.app.infrastructure.persistence.generic.CrudDao;

public interface SeatDao extends CrudDao<Seat,Long> {
}
