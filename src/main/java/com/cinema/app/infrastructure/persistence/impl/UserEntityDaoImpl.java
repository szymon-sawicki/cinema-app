package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.domain.user.User;
import com.cinema.app.infrastructure.persistence.dao.UserEntityDao;
import com.cinema.app.infrastructure.persistence.entity.UserEntity;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserEntityDaoImpl extends AbstractCrudDao<UserEntity, Long> implements UserEntityDao {
    public UserEntityDaoImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public Optional<UserEntity> findByName(String name) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from users where name = :name")
                .bind("name",name)
                .mapToBean(UserEntity.class)
                .findFirst());
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from users where username = :username")
                .bind("username",username)
                .mapToBean(UserEntity.class)
                .findFirst());
    }
}
