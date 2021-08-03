package com.cinema.app.infrastructure.persistence.generic;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T, ID> {

    Optional<T> save(T t);
    Optional<T> update(ID id, T t);
    Optional<T> findById(ID id);
    List<T> saveAll(List<T> items);
    List<T> findAllById(List<ID> ids);
    List<T> findAll();
    Optional<T> deleteById(ID id);
    List<T> deleteAllById(List<ID> ids);

}
