package com.cinema.app.infrastructure.persistence;

import com.cinema.app.infrastructure.persistence.entity.SeatEntity;
import com.cinema.app.infrastructure.persistence.generic.CrudDao;

import java.util.List;

public interface SeatEntityDao extends CrudDao<SeatEntity,Long> {

    List<SeatEntity> findAllSeatsFromRoom(Long roomId);

}
