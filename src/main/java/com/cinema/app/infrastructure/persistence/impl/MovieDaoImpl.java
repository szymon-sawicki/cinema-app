package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.domain.movie.Movie;
import com.cinema.app.domain.movie.type.MovieGenre;
import com.cinema.app.infrastructure.persistence.MovieDao;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MovieDaoImpl extends AbstractCrudDao<Movie,Long> implements MovieDao {

    public MovieDaoImpl(Jdbi jdbi) { super(jdbi); }

    @Override
    public Optional<Movie> findByName(String name) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from movies where name = :name")
                .bind("name", name)
                .mapToBean(Movie.class)
                .findFirst());
    }

    @Override
    public List<Movie> findMoviesByGenre(MovieGenre movieGenre) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from movies where movie_genre = :movieGenre")
                .bind("movieGenre", movieGenre)
                .mapToBean(Movie.class)
                .list());
    }
}

