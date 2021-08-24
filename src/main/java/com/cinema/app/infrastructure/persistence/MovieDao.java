package com.cinema.app.infrastructure.persistence;

import com.cinema.app.domain.movie.Movie;
import com.cinema.app.domain.movie.type.MovieGenre;
import com.cinema.app.infrastructure.persistence.generic.CrudDao;

import java.util.List;
import java.util.Optional;

public interface MovieDao extends CrudDao<Movie, Long> {
    Optional<Movie> findByName(String name);
    List<Movie> findMoviesByGenre(MovieGenre movieGenre);
}
