package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.domain.movie.Movie;
import com.cinema.app.infrastructure.persistence.MovieDao;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;

import java.util.Optional;

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
    }

