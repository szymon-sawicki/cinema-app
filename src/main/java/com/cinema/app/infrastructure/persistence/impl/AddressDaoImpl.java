package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.domain.address.Address;
import com.cinema.app.infrastructure.persistence.AddressDao;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
