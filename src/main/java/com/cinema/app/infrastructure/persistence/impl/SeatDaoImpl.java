package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.domain.seat.Seat;
import com.cinema.app.infrastructure.persistence.SeatDao;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class SeatDaoImpl extends AbstractCrudDao<Seat, Long> implements SeatDao {

    protected SeatDaoImpl(Jdbi jdbi) { super(jdbi); }

    @Override
    public List<Seat> findAllSeatsFromRoom(Long roomId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from seats where room_id = :room_id")
                .bind("room_id",roomId)
                .mapToBean(Seat.class)
                .list());
    }
}
