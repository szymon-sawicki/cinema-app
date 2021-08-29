package com.cinema.app.infrastructure.persistence;

import com.cinema.app.domain.cinema_room.CinemaRoom;
import com.cinema.app.infrastructure.persistence.entity.CinemaRoomEntity;
import com.cinema.app.infrastructure.persistence.generic.CrudDao;

import java.util.List;

public interface CinemaRoomEntityDao extends CrudDao<CinemaRoomEntity, Long> {

    List<CinemaRoomEntity> findAllRoomsFromCinema(Long cinemaId);

}
