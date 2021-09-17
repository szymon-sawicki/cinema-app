package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.infrastructure.persistence.dao.CinemaRoomEntityDao;
import com.cinema.app.infrastructure.persistence.entity.CinemaRoomEntity;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CinemaRoomEntityDaoImpl extends AbstractCrudDao<CinemaRoomEntity, Long> implements CinemaRoomEntityDao {

    protected CinemaRoomEntityDaoImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public List<CinemaRoomEntity> findAllRoomsFromCinema(Long cinemaId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from cinema_rooms where cinema_id = :cinema_id")
                .bind("cinema_id", cinemaId)
                .mapToBean(CinemaRoomEntity.class)
                .list()
        );
    }

    @Override
    public List<Long> findAllIdsFromCinema(Long cinemaId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select id from cinema_rooms where cinema_id = :cinema_id")
                .bind("cinema_id", cinemaId)
                .mapTo(Long.class)
                .list());
    }


}
