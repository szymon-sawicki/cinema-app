package com.cinema.app.application.service;

import com.cinema.app.infrastructure.persistence.CinemaRoomDao;
import com.cinema.app.infrastructure.persistence.MovieDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ScreeningsService {

    private final MovieDao movieDao;
    private final CinemaRoomDao cinemaRoomDao;

    /*
        select c.name as cinemaName, ... where c.name like %:expr% or a.city like %:expr% or a.street like %:expr% ...
    */


}
