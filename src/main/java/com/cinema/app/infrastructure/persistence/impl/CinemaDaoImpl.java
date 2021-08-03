package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.domain.cinema.Cinema;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;


public class CinemaDaoImpl extends AbstractCrudDao<Cinema,Long> implements CinemaDao {

    protected CinemaDaoImpl(Jdbi jdbi) {
        super(jdbi);
    }
}
