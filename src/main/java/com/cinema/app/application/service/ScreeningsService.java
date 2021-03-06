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
import com.cinema.app.domain.screening.dto.GetScreeningInfoDto;
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
     *
     * @param createUpdateScreeningDto screening to be created
     * @return screening inserted into db
     */

    public GetScreeningDto createScreeening(CreateUpdateScreeningDto createUpdateScreeningDto) {

        Validator.validate(new CreateUpdateScreeningDtoValidator(), createUpdateScreeningDto);
        checkTimeAvailability(createUpdateScreeningDto);

        var dateTime = createUpdateScreeningDto.getDateTime();
        var createMovieDto = createUpdateScreeningDto.getCreateUpdateMovieDto();
        var movieEntity = createMovieDto.toMovie().toEntity();


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
    // it fetches all screenings in cinema room at given date and compares with time of screening to create/update

    private void checkTimeAvailability(CreateUpdateScreeningDto createUpdateScreeningDto) {

        var dateTime = createUpdateScreeningDto.getDateTime();
        var timeToCheck = dateTime.toLocalTime();
        var duration = createUpdateScreeningDto.getCreateUpdateMovieDto().getLength();

        var getScreeningDtos = screeningEntityDao.findAllByCinemaRoomAndDate(createUpdateScreeningDto.getCinemaRoomId(), dateTime.toLocalDate())
                .stream()
                .map(screeningEntity -> screeningEntity.toScreening().toGetScreeningDto())
                .toList();

        if (!getScreeningDtos.isEmpty()) {

            getScreeningDtos.stream().forEach(getScreeningDto -> {

                var screeningTime = getScreeningDto.getDateTime().toLocalTime();
                var endOfScreening = screeningTime.plusMinutes(movieEntityDao
                        .findById(getScreeningDto.getMovieId()).orElseThrow().toMovie().toGetMovieDto().getLength());
// TODO compare
                if (screeningTime.equals(timeToCheck) || (screeningTime.isBefore(timeToCheck) && endOfScreening.isAfter(timeToCheck)) || (screeningTime.isAfter(timeToCheck) && timeToCheck.plusMinutes(duration).isAfter(screeningTime))) {
                    throw new ScreeningsServiceException("cannot add screening - that time is already booked");
                }
            });

        }
    }

    /**
     * method finding all screenings of given movie
     *
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

        return screeningInfoDao.findByMovie(movieId)
                .stream()
                .map(ScreeningInfo::toGetScreeningInfoDto)
                .toList();

    }

    /**
     * method finding all screenings in given date
     *
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
     * method finding all screenings
     * @return list fo screenings
     */

    public List<GetScreeningInfoDto> findAll() {

        var result = screeningInfoDao.findAll()
                .stream()
                .map(ScreeningInfo::toGetScreeningInfoDto)
                .toList();

        if(result.isEmpty()) {
            throw new ScreeningsServiceException("cannot find any screenings");
        }
        return result;

     }

    /**
     * method finding all screenings of given cinema
     *
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
     *
     * @param keyword to be searched
     * @return screenings matching keyword
     */

    public List<GetScreeningInfoDto> findByKeyword(String keyword) {
        if (keyword == null) {
            throw new ScreeningsServiceException("keyword is null");
        }

        return screeningInfoDao.findByKeyword(keyword)
                .stream()
                .map(ScreeningInfo::toGetScreeningInfoDto)
                .toList();
    }

    /**
     * method updating screening in database
     *
     * @param screeningId              screening to update
     * @param createUpdateScreeningDto updating data
     * @return updated screening
     */

    public GetScreeningDto updateScreening(Long screeningId, CreateUpdateScreeningDto createUpdateScreeningDto) {

        Validator.validate(new CreateUpdateScreeningDtoValidator(), createUpdateScreeningDto);
        checkTimeAvailability(createUpdateScreeningDto);

        if (screeningId == null) {
            throw new ScreeningsServiceException("screening id is null");
        }
        if (screeningId <= 0) {
            throw new ScreeningsServiceException("screening id is 0 or negative");
        }

        var movieDto = createUpdateScreeningDto.getCreateUpdateMovieDto();

        var movieFromDb = movieEntityDao.findByTitle(movieDto.getTitle())
                .orElseGet(() -> movieEntityDao
                        .save(movieDto.toMovie().toEntity())
                        .orElseThrow(() -> new ScreeningsServiceException("cannot add new movie")))
                .toMovie();


        return screeningEntityDao.update(screeningId, createUpdateScreeningDto
                        .toScreening()
                        .withMovieId(MovieUtils.toId.apply(movieFromDb))
                        .toEntity())
                .orElseThrow(() -> new ScreeningsServiceException("cannot update screening"))
                .toScreening()
                .toGetScreeningDto();
    }

    /**
     * method used to  delete given screening
     *
     * @param screeningId screening to delete
     * @return deleted screening
     */

    public GetScreeningDto deleteScreening(Long screeningId) {

        if (screeningId == null) {
            throw new ScreeningsServiceException("screening id is null");
        }
        if (screeningId <= 0) {
            throw new ScreeningsServiceException("screening id is 0 or negative");
        }

        return screeningEntityDao.deleteById(screeningId)
                .orElseThrow(() -> new ScreeningsServiceException("cannot delete screening"))
                .toScreening()
                .toGetScreeningDto();
    }

    // TODO make method finding screenign from given cinema and date
}
