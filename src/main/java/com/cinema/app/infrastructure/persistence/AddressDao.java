package com.cinema.app.infrastructure.persistence;

import com.cinema.app.domain.address.Address;
import com.cinema.app.infrastructure.persistence.generic.CrudDao;

import java.util.List;
import java.util.Optional;

public interface AddressDao extends CrudDao<Address, Long> {

    List<Address> findAllFromCity(String city);
    Optional<Address> findAddress(String street, String houseNumber, String city, String zipCode);
    List<Long> findAllIdsFromCity(String city);

}
