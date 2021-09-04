package com.cinema.app.infrastructure.persistence.dao;

import com.cinema.app.infrastructure.persistence.entity.SeatEntity;
import com.cinema.app.infrastructure.persistence.generic.CrudDao;

import java.util.List;

public interface SeatEntityDao extends CrudDao<SeatEntity,Long> {
    List<SeatEntity> findSeatsByCinemaRoom(Long cinemaRoomId);
    List<Long> findIdsByCinemaRoom(Long cinemaRoomId);

}
