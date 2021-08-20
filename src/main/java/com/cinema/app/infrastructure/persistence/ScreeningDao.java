package com.cinema.app.infrastructure.persistence;

import com.cinema.app.domain.screening.Screening;

import java.util.List;

public interface ScreeningDao {
    List<Screening> findAllByMovieId(Long movieId);
}
