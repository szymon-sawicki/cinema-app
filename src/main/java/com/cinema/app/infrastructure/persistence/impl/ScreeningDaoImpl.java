package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.domain.screening.Screening;
import com.cinema.app.infrastructure.persistence.ScreeningDao;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ScreeningDaoImpl extends AbstractCrudDao<Screening, Long> implements ScreeningDao {

    public ScreeningDaoImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public List<Screening> findAllByMovieId(Long movieId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from cinemas where movie_id = :movie_id")
                .bind("movie_id", movieId)
                .mapToBean(Screening.class)
                .list());
    }

    @Override
    public List<Screening> findAllByCinemaRoomAndDate(Long cinemaRoomId, LocalDate date) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from screenings where cinema_room_id = :cinemaRoomId AND date = :date")
                .bind("cinemaRoomId",cinemaRoomId)
                .bind("date",date)
                .mapToBean(Screening.class)
                .list()
        );
    }
}

