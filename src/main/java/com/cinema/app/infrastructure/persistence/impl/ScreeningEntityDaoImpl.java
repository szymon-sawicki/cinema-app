package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.infrastructure.persistence.dao.ScreeningEntityDao;
import com.cinema.app.infrastructure.persistence.entity.ScreeningEntity;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ScreeningEntityDaoImpl extends AbstractCrudDao<ScreeningEntity, Long> implements ScreeningEntityDao {

    public ScreeningEntityDaoImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public List<ScreeningEntity> findAllByMovieId(Long movieId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from cinemas where movie_id = :movie_id")
                .bind("movie_id", movieId)
                .mapToBean(ScreeningEntity.class)
                .list());
    }

    @Override
    public List<ScreeningEntity> findAllByCinemaRoomAndDate(Long cinemaRoomId, LocalDate date) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from screenings where cinema_room_id = :cinemaRoomId AND DATE(date_time) = :date")
                .bind("cinemaRoomId",cinemaRoomId)
                .bind("date",date)
                .mapToBean(ScreeningEntity.class)
                .list()
        );
    }
}

