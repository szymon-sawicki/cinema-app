package com.cinema.app.infrastructure.persistence.impl;


import com.cinema.app.infrastructure.persistence.dao.SeatEntityDao;
import com.cinema.app.infrastructure.persistence.entity.SeatEntity;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SeatEntityDaoImpl extends AbstractCrudDao<SeatEntity, Long> implements SeatEntityDao {

    protected SeatEntityDaoImpl(Jdbi jdbi) { super(jdbi); }

    @Override
    public List<SeatEntity> findSeatsByCinemaRoom(Long cinemaRoomId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from seats where cinema_room_id = :room_id")
                .bind("room_id",cinemaRoomId)
                .mapToBean(SeatEntity.class)
                .list());
    }

}
