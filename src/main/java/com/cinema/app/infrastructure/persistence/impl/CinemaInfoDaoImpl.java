package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.infrastructure.persistence.dao.CinemaInfoDao;
import com.cinema.app.infrastructure.persistence.entity.view.CinemaInfo;
import com.cinema.app.infrastructure.persistence.entity.view.ScreeningInfo;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CinemaInfoDaoImpl extends AbstractCrudDao<CinemaInfo,Long> implements CinemaInfoDao {

    protected CinemaInfoDaoImpl(Jdbi jdbi) { super(jdbi); }

    private final String selectAndJoinExpression = """
            SELECT c.name as name, a.street as street, a.house_number as houseNumber, a.city as city, a.zip_code as zipCode FROM
            addresses a join cinemas c on a.id = c.address_id
            """;

    @Override
    public List<CinemaInfo> findByCity(String city) {

        var sql = selectAndJoinExpression + " WHERE a.city LIKE :city";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("city",city)
                .mapToBean(CinemaInfo.class)
                .list());
    }

    @Override
    public Optional<CinemaInfo> findByName(String name) {

        var sql = selectAndJoinExpression + " WHERE c.name LIKE :name";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("name",name)
                .mapToBean(CinemaInfo.class)
                .findFirst());
    }
}
