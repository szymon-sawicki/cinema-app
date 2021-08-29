package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.infrastructure.persistence.dao.AddressEntityDao;
import com.cinema.app.infrastructure.persistence.entity.AddressEntity;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public class AddressEntityDaoImpl extends AbstractCrudDao<AddressEntity, Long> implements AddressEntityDao {

    protected AddressEntityDaoImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public List<AddressEntity> findAllFromCity(String city) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from addresses where city = :city")
                .bind("city",city.toLowerCase(Locale.ROOT))
                .mapToBean(AddressEntity.class)
                .list());
    }

    @Override
    public Optional<AddressEntity> findAddress(String street, String houseNumber, String city, String zipCode) {
        return jdbi.withHandle(handle -> handle
                .createQuery("""
                select * from addresses where street = :street AND house_number = :houseNumber AND city = :city AND zip_code = :zipCode
                """)
                .bind("city",city.toLowerCase(Locale.ROOT))
                .bind("street",street.toLowerCase(Locale.ROOT))
                .bind("houseNumber",houseNumber)
                .bind("zipCode",zipCode)
                .mapToBean(AddressEntity.class)
                .findFirst());
    }

    @Override
    public List<Long> findAllIdsFromCity(String city) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select id from addresses where city = :city")
                .bind("city",city)
                .mapTo(Long.class)
                .list()
        );
    }
}
