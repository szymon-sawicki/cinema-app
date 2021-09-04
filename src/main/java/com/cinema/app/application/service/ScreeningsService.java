package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.ScreeningsServiceException;
import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.movie.MovieUtils;
import com.cinema.app.domain.screening.dto.CreateUpdateScreeningDto;
import com.cinema.app.domain.screening.dto.GetScreeningDto;
import com.cinema.app.domain.screening.dto.validator.CreateUpdateScreeningDtoValidator;
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

    /**
     * method creating new movie's screening in given cinema room
     * @param createUpdateScreeningDto screening to be created
     * @return screening inserted into db
     */

    public GetScreeningDto createScreeening(CreateUpdateScreeningDto createUpdateScreeningDto) {
        Validator.validate(new CreateUpdateScreeningDtoValidator(), createUpdateScreeningDto);

        var dateTime = createUpdateScreeningDto.getDateTime();
        var time = dateTime.toLocalTime();
        var createMovieDto = createUpdateScreeningDto.getCreateUpdateMovieDto();
        var duration = createMovieDto.getLength();
        var movieEntity = createMovieDto.toMovie().toEntity();
        var cinemaRoomId = createUpdateScreeningDto.getCinemaRoomId();

        var screeningsFromDay = screeningEntityDao.findAllByCinemaRoomAndDate(cinemaRoomId, dateTime.toLocalDate())
                .stream()
                .map(screeningEntity -> screeningEntity.toScreening().toGetScreeningDto())
                .toList();
// TODO pobrac liste godzin
        if (!screeningsFromDay.isEmpty()) {
            checkTimeAvailability(time, duration, screeningsFromDay);
        }

        var movieFromDb = movieEntityDao.findByTitle(createMovieDto.getTitle())
                .orElseGet(() -> movieEntityDao
                        .save(movieEntity)
                        .orElseThrow(() -> new ScreeningsServiceException("cannot add new movie")))
                .toMovie();

        var screeningToInsert = createUpdateScreeningDto
                .toScreening()
                .withMovieId(MovieUtils.toId.apply(movieFromDb))
                .toEntity();

        return screeningEntityDao
                .save(screeningToInsert)
                .orElseThrow(() -> new ScreeningsServiceException("cannot add new screening"))
                .toScreening()
                .toGetScreeningDto();

    }

    // private method checking availibility of given screening's time in GetScrreningDto list

    private void checkTimeAvailability(LocalTime timeToCheck, Integer movieDuration, List<GetScreeningDto> getScreeningDtos) {
        getScreeningDtos.stream().forEach(getScreeningDto -> {

            var screeningTime = getScreeningDto.getDateTime().toLocalTime();
            var endOfScreening = screeningTime.plusMinutes(movieEntityDao
                    .findById(getScreeningDto.getMovieId()).orElseThrow().toMovie().toGetMovieDto().getLength());
// TODO compare
            if ((screeningTime.isBefore(timeToCheck) && endOfScreening.isAfter(timeToCheck)) || (screeningTime.isAfter(timeToCheck) && timeToCheck.plusMinutes(movieDuration).isAfter(screeningTime))) {
                throw new ScreeningsServiceException("cannot add screening - that time is already booked");
            }
        });
    }

    /**
     * method finding all screenings of given movie
     * @param movieId movie to be searched
     * @return list with searched movies
     */

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

    /**
     * method finding all screenings in given date
     * @param date to be searched
     * @return list of ScreeningInfo view with given date
     */

    public List<GetScreeningInfoDto> findByDate(LocalDate date) {
        if (date == null) {
            throw new ScreeningsServiceException("date is null");
        }

        return screeningInfoDao.findByDate(date)
                .stream()
                .map(ScreeningInfo::toGetScreeningInfoDto)
                .toList();

    }

    /**
     * method finding all screenings of given cinema
     * @param cinemaId to be searched
     * @return list of screenings in given cinema
     */

    public List<GetScreeningInfoDto> findByCinema(Long cinemaId) {
        if (cinemaId == null) {
            throw new ScreeningsServiceException("movie id is null");
        }
        if (cinemaId <= 0) {
            throw new ScreeningsServiceException("cinema room id is 0 or negative");
        }

        return screeningInfoDao.findByCinema(cinemaId).stream()
                .map(ScreeningInfo::toGetScreeningInfoDto)
                .toList();
    }

    /**
     * method used to finding screening by given keyword.
     * searching goes through fields: street, city, cinema name, cinema room name, movie title
     * @param keyword to be searched
     * @return screenings matching keyword
     */

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
