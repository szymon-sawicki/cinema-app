package com.cinema.app.infrastructure.persistence.dao;

import com.cinema.app.infrastructure.persistence.entity.AddressEntity;
import com.cinema.app.infrastructure.persistence.generic.CrudDao;

import java.util.List;
import java.util.Optional;

public interface AddressEntityDao extends CrudDao<AddressEntity, Long> {

    List<AddressEntity> findAllFromCity(String city);
    Optional<AddressEntity> findAddress(String street, String houseNumber, String city, String zipCode);
    List<Long> findAllIdsFromCity(String city);

}
