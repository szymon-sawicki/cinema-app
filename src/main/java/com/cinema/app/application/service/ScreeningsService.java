package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.ScreeningsServiceException;
import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.movie.MovieUtils;
import com.cinema.app.domain.screening.dto.CreateScreeningDto;
import com.cinema.app.domain.screening.dto.GetScreeningDto;
import com.cinema.app.domain.screening.dto.validator.CreateScreeningDtoValidator;
import com.cinema.app.infrastructure.persistence.dao.CinemaRoomEntityDao;
import com.cinema.app.infrastructure.persistence.dao.MovieEntityDao;
import com.cinema.app.infrastructure.persistence.dao.ScreeningEntityDao;
import com.cinema.app.infrastructure.persistence.dao.ScreeningInfoDao;
import com.cinema.app.infrastructure.persistence.entity.view.ScreeningInfo;
import com.cinema.app.screening.dto.GetScreeningInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ScreeningsService {

    private final MovieEntityDao movieEntityDao;
    private final CinemaRoomEntityDao cinemaRoomEntityDao;
    private final ScreeningEntityDao screeningEntityDao;
    private final ScreeningInfoDao screeningInfoDao;

    /*
        select c.name as cinemaName, ... where c.name like %:expr% or a.city like %:expr% or a.street like %:expr% ...
    */

    public GetScreeningDto createScreeening(CreateScreeningDto createScreeningDto) {
        Validator.validate(new CreateScreeningDtoValidator(), createScreeningDto);

        var dateTime = createScreeningDto.getDateTime();
        var time = dateTime.toLocalTime();
        var createMovieDto = createScreeningDto.getCreateMovieDto();
        var duration = createMovieDto.getLength();
        var movieEntity = createMovieDto.toMovie().toEntity();
        var cinemaRoomId = createScreeningDto.getCinemaRoomId();

        var screeningsFromDay = screeningEntityDao.findAllByCinemaRoomAndDate(cinemaRoomId, dateTime.toLocalDate())
                .stream()
                .map(screeningEntity -> screeningEntity.toScreening().toGetScreeningDto())
                .toList();

        if (!screeningsFromDay.isEmpty()) {
            checkTimeAvailability(time, duration, screeningsFromDay);
        }

        var movieFromDb = movieEntityDao.findByTitle(createMovieDto.getTitle())
                .orElseGet(() -> movieEntityDao
                        .save(movieEntity)
                        .orElseThrow(() -> new ScreeningsServiceException("cannot add new movie")))
                .toMovie();

        var screeningToInsert = createScreeningDto
                .toScreening()
                .withMovieId(MovieUtils.toId.apply(movieFromDb))
                .toEntity();

        return screeningEntityDao
                .save(screeningToInsert)
                .orElseThrow(() -> new ScreeningsServiceException("cannot add new screening"))
                .toScreening()
                .toGetScreeningDto();

    }

    private void checkTimeAvailability(LocalTime timeToCheck, Integer movieDuration, List<GetScreeningDto> getScreeningDtos) {
        getScreeningDtos.stream().forEach(getScreeningDto -> {

            var screeningTime = getScreeningDto.getDateTime().toLocalTime();
            var endOfScreening = screeningTime.plusMinutes(movieEntityDao
                    .findById(getScreeningDto.getMovieId()).orElseThrow().toMovie().toGetMovieDto().getLength());

            if ((screeningTime.isBefore(timeToCheck) && endOfScreening.isAfter(timeToCheck)) || (screeningTime.isAfter(timeToCheck) && timeToCheck.plusMinutes(movieDuration).isAfter(screeningTime))) {
                throw new ScreeningsServiceException("cannot add screening - that time is already booked");
            }
        });
    }

    public List<GetScreeningInfoDto> findByMovie(Long movieId) {
        if (movieId == null) {
            throw new ScreeningsServiceException("movie id is null");
        }
        if (movieId <= 0) {
            throw new ScreeningsServiceException("cinema room id is 0 or negative");
        }

        // TODO impl

        return null;

    }

    public List<GetScreeningInfoDto> findByDate(LocalDate date) {
        if (date == null) {
            throw new ScreeningsServiceException("date is null");
        }

        // TODO impl

        return null;

    }

    public List<GetScreeningInfoDto> findByCinema(Long cinemaId) {
        if (cinemaId == null) {
            throw new ScreeningsServiceException("movie id is null");
        }
        if (cinemaId <= 0) {
            throw new ScreeningsServiceException("cinema room id is 0 or negative");
        }

        // TODO impl

        return null;

    }

    public List<GetScreeningInfoDto> findByKeyword(String keyword) {
        if(keyword == null) {
            throw new ScreeningsServiceException("keyword is null");
        }

        return screeningInfoDao.findByKeyword(keyword)
                .stream()
                .map(ScreeningInfo::toGetScreeningInfoDto)
                .toList();
    }




}
