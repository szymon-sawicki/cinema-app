package com.cinema.app.infrastructure.persistence.dao;

import com.cinema.app.domain.movie.type.MovieGenre;
import com.cinema.app.infrastructure.persistence.entity.MovieEntity;
import com.cinema.app.infrastructure.persistence.generic.CrudDao;

import java.util.List;
import java.util.Optional;

public interface MovieEntityDao extends CrudDao<MovieEntity, Long> {
    Optional<MovieEntity> findByTitle(String name);
    List<MovieEntity> findMoviesByGenre(MovieGenre movieGenre);
}
