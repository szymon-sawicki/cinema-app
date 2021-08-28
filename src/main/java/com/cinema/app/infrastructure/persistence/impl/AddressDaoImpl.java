package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.domain.address.Address;
import com.cinema.app.infrastructure.persistence.AddressDao;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public class AddressDaoImpl extends AbstractCrudDao<Address, Long> implements AddressDao {

    protected AddressDaoImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public List<Address> findAllFromCity(String city) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from addresses where city = :city")
                .bind("city",city.toLowerCase(Locale.ROOT))
                .mapToBean(Address.class)
                .list());
    }

    @Override
    public Optional<Address> findAddress(String street, String houseNumber, String city, String zipCode) {
        return jdbi.withHandle(handle -> handle
                .createQuery("""
                select * from addresses where street = :street AND house_number = :houseNumber AND city = :city AND zip_code = :zipCode
                """)
                .bind("city",city.toLowerCase(Locale.ROOT))
                .bind("street",street.toLowerCase(Locale.ROOT))
                .bind("houseNumber",houseNumber)
                .bind("zipCode",zipCode)
                .mapToBean(Address.class)
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
