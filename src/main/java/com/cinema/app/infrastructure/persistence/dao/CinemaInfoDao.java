package com.cinema.app.infrastructure.persistence.dao;

import com.cinema.app.infrastructure.persistence.entity.view.CinemaInfo;

import java.util.List;
import java.util.Optional;

public interface CinemaInfoDao {
    List<CinemaInfo> findByCity(String city);
    Optional<CinemaInfo> findByName(String name);
}
