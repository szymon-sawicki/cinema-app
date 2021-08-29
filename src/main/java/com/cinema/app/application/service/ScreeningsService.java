package com.cinema.app.application.service;

import com.cinema.app.infrastructure.persistence.dao.CinemaRoomEntityDao;
import com.cinema.app.infrastructure.persistence.dao.MovieEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ScreeningsService {

    private final MovieEntityDao movieEntityDao;
    private final CinemaRoomEntityDao cinemaRoomEntityDao;

    /*
        select c.name as cinemaName, ... where c.name like %:expr% or a.city like %:expr% or a.street like %:expr% ...
    */


}
