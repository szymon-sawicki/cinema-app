package com.cinema.app.infrastructure.persistence.dao;

import com.cinema.app.infrastructure.persistence.entity.view.ScreeningInfo;

import java.util.List;

public interface ScreeningInfoDao {
    List<ScreeningInfo> findByKeyword(String keyword);
}