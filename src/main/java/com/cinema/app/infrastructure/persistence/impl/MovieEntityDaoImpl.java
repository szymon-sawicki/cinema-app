package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.domain.movie.type.MovieGenre;
import com.cinema.app.infrastructure.persistence.dao.MovieEntityDao;
import com.cinema.app.infrastructure.persistence.entity.MovieEntity;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MovieEntityDaoImpl extends AbstractCrudDao<MovieEntity,Long> implements MovieEntityDao {

    protected MovieEntityDaoImpl(Jdbi jdbi) { super(jdbi); }

    @Override
    public Optional<MovieEntity> findByTitle(String title) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from movies where title = :title")
                .bind("title", title)
                .mapToBean(MovieEntity.class)
                .findFirst());
    }

    @Override
    public List<MovieEntity> findMoviesByGenre(MovieGenre movieGenre) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from movies where movie_genre = :movieGenre")
                .bind("movieGenre", movieGenre)
                .mapToBean(MovieEntity.class)
                .list());
    }
}

