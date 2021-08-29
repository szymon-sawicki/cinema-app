package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.infrastructure.persistence.CinemaEntityDao;
import com.cinema.app.infrastructure.persistence.entity.CinemaEntity;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.Optional;

@Repository
public class CinemaEntityDaoImpl extends AbstractCrudDao<CinemaEntity, Long> implements CinemaEntityDao {

    protected CinemaEntityDaoImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public Optional<CinemaEntity> findByAddress(Long addressId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from cinemas where address_id = :address_id")
                .bind("address_id", addressId)
                .mapToBean(CinemaEntity.class)
                .findFirst());
    }

    @Override
    public Optional<CinemaEntity> findByName(String name) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from cinemas where name = :name")
                .bind("name", name.toLowerCase(Locale.ROOT))
                .mapToBean(CinemaEntity.class)
                .findFirst());
    }
}
