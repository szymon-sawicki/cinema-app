package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.infrastructure.persistence.dao.TicketEntityDao;
import com.cinema.app.infrastructure.persistence.entity.TicketEntity;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TicketEntityDaoImpl extends AbstractCrudDao<TicketEntity, Long> implements TicketEntityDao {

    protected TicketEntityDaoImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public List<TicketEntity> findAllByScreeningId(Long id) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from tickets where screening_id = :id")
                .bind("id",id)
                .mapToBean(TicketEntity.class)
                .list()
        );
    }
}
