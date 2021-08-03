package com.cinema.app.infrastructure.persistence;

import com.cinema.app.domain.cinema.Cinema;
import com.cinema.app.infrastructure.persistence.generic.CrudDao;

public interface CinemaDao extends CrudDao<Cinema, Long> {

    Optional<Cinema> findByAddress(Long id);
    Optional<Cinema> findByName(String name);

}
