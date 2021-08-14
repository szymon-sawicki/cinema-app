package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.domain.address.Address;
import com.cinema.app.infrastructure.persistence.AddressDao;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;
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
                .bind("city",city)
                .mapToBean(Address.class)
                .list());
    }

    @Override
    public Optional<Address> findAddress(String street, String houseNumber, String city, String zipCode) {
        return Optional.empty();
    }
}
