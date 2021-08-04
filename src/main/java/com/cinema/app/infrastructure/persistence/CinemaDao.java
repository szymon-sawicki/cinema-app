package com.cinema.app.infrastructure.persistence;

import com.cinema.app.domain.cinema.Cinema;
import com.cinema.app.infrastructure.persistence.generic.CrudDao;

import java.util.List;
import java.util.Optional;

public interface CinemaDao extends CrudDao<Cinema, Long> {

    Optional<Cinema> findByAddress(Long addressId);
    Optional<Cinema> findByName(String name);


}
