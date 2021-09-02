package com.cinema.app.infrastructure.persistence.dao;

import com.cinema.app.infrastructure.persistence.entity.TicketEntity;
import com.cinema.app.infrastructure.persistence.generic.CrudDao;

import java.util.List;
import java.util.Optional;

public interface TicketEntityDao extends CrudDao<TicketEntity,Long> {
    List<TicketEntity> findAllByScreeningId(Long id);
}
