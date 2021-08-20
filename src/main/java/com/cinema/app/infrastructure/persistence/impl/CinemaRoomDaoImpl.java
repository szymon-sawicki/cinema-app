package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.domain.cinema_room.CinemaRoom;
import com.cinema.app.infrastructure.persistence.CinemaRoomDao;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CinemaRoomDaoImpl extends AbstractCrudDao<CinemaRoom,Long> implements CinemaRoomDao {

    public CinemaRoomDaoImpl(Jdbi jdbi) { super(jdbi); }

    @Override
    public List<CinemaRoom> findAllRoomsFromCinema(Long cinemaId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from cinema_rooms where cinema_id = :cinema_id")
                .bind("cinema_id", cinemaId)
                .mapToBean(CinemaRoom.class)
                .list()
        );
    }
}
