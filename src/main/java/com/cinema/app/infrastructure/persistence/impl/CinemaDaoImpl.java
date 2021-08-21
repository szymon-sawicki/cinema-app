package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.domain.cinema.Cinema;
import com.cinema.app.infrastructure.persistence.CinemaDao;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.Optional;

@Repository
public class CinemaDaoImpl extends AbstractCrudDao<Cinema, Long> implements CinemaDao {

    protected CinemaDaoImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public Optional<Cinema> findByAddress(Long addressId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from cinemas where address_id = :address_id")
                .bind("address_id", addressId)
                .mapToBean(Cinema.class)
                .findFirst());
    }

    @Override
    public Optional<Cinema> findByName(String name) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from cinemas where name = :name")
                .bind("name", name.toLowerCase(Locale.ROOT))
                .mapToBean(Cinema.class)
                .findFirst());
    }
}
