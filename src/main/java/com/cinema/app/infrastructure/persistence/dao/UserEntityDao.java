package com.cinema.app.infrastructure.persistence.dao;

import com.cinema.app.infrastructure.persistence.entity.UserEntity;
import com.cinema.app.infrastructure.persistence.generic.CrudDao;

import java.util.Optional;

public interface UserEntityDao extends CrudDao<UserEntity, Long> {
    Optional<UserEntity> findByName(String name);
    Optional<UserEntity> findByUsername(String name);
    Optional<UserEntity> findByMail(String mail);
}
