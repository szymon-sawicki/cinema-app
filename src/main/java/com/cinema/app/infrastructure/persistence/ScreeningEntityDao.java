package com.cinema.app.infrastructure.persistence;

import com.cinema.app.domain.screening.Screening;
import com.cinema.app.infrastructure.persistence.entity.ScreeningEntity;
import com.cinema.app.infrastructure.persistence.generic.CrudDao;

import java.time.LocalDate;
import java.util.List;

public interface ScreeningEntityDao extends CrudDao<ScreeningEntity,Long> {

    List<ScreeningEntity> findAllByMovieId(Long movieId);
    List<ScreeningEntity> findAllByCinemaRoomAndDate(Long cinemaRoomId, LocalDate date);

}
