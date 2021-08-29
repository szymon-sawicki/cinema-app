package com.cinema.app.infrastructure.persistence;

import com.cinema.app.domain.cinema.Cinema;
import com.cinema.app.infrastructure.persistence.entity.CinemaEntity;
import com.cinema.app.infrastructure.persistence.generic.CrudDao;

import java.util.List;
import java.util.Optional;

public interface CinemaEntityDao extends CrudDao<CinemaEntity, Long> {

    Optional<CinemaEntity> findByAddress(Long addressId);
    Optional<CinemaEntity> findByName(String name);

}
