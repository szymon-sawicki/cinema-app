package com.cinema.app.infrastructure.persistence.dao;

import com.cinema.app.infrastructure.persistence.entity.SeatEntity;
import com.cinema.app.infrastructure.persistence.entity.view.ScreeningInfo;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import com.cinema.app.infrastructure.persistence.generic.CrudDao;

import java.time.LocalDate;
import java.util.List;

public interface ScreeningInfoDao {
    List<ScreeningInfo> findByKeyword(String keyword);
    List<ScreeningInfo> findByCinema(Long cinemaId);
    List<ScreeningInfo> findByMovie(Long movieId);
    List<ScreeningInfo> findByDate(LocalDate date);
    List<ScreeningInfo> findAll();
}
