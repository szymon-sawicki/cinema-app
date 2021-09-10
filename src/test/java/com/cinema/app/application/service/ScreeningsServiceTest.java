package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.ScreeningsServiceException;
import com.cinema.app.domain.movie.dto.CreateUpdateMovieDto;
import com.cinema.app.domain.movie.type.MovieGenre;
import com.cinema.app.domain.screening.dto.CreateUpdateScreeningDto;
import com.cinema.app.domain.screening.dto.GetScreeningDto;
import com.cinema.app.infrastructure.persistence.dao.MovieEntityDao;
import com.cinema.app.infrastructure.persistence.dao.ScreeningEntityDao;
import com.cinema.app.infrastructure.persistence.dao.ScreeningInfoDao;
import com.cinema.app.infrastructure.persistence.entity.*;
import com.cinema.app.infrastructure.persistence.entity.view.ScreeningInfo;
import com.cinema.app.domain.screening.dto.GetScreeningInfoDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

public class ScreeningsServiceTest {

    @Mock
    private ScreeningEntityDao screeningEntityDao;

    @Mock
    private MovieEntityDao movieEntityDao;

    @Mock
    private ScreeningInfoDao screeningInfoDao;

    @InjectMocks
    private ScreeningsService screeningsService;

    @Test
    @DisplayName("when time of screening is already booked after given time")
    public void test1() {

        var cinemaRoomId = 3L;
        var movieId = 1L;
        var movieTitle = "Andreas Abenteuer";

        var movieFromDb = MovieEntity.builder()
                .id(movieId)
                .movieGenre(MovieGenre.HORROR)
                .length(90)
                .title(movieTitle)
                .build();


        var screening = ScreeningEntity.builder()
                .cinemaRoomId(cinemaRoomId)
                .movieId(movieId)
                .dateTime(LocalDateTime.of(2021, 10, 1, 19, 30))
                .build();


        var screeningToInsert = CreateUpdateScreeningDto.builder()
                .cinemaRoomId(cinemaRoomId)
                .createUpdateMovieDto(CreateUpdateMovieDto.builder().title(movieTitle).length(90).build())
                .dateTime(LocalDateTime.of(2021, 10, 1, 18, 30))
                .build();

        when(movieEntityDao.findByTitle(movieTitle))
                .thenReturn(Optional.of(movieFromDb));

        when(movieEntityDao.findById(movieId))
                .thenReturn(Optional.of(movieFromDb));

        when(screeningEntityDao.findAllByCinemaRoomAndDate(any(), any()))
                .thenReturn(List.of(screening));

        assertThatThrownBy(() -> screeningsService.createScreeening(screeningToInsert))
                .isInstanceOf(ScreeningsServiceException.class)
                .hasMessageContaining("cannot add screening - that time is already booked");

    }

    @Test
    @DisplayName("when time of screening is already booked before given time")
    public void test2() {

        var cinemaRoomId = 3L;
        var movieId = 1L;
        var movieTitle = "Andreas Abenteuer";

        var movieFromDb = MovieEntity.builder()
                .id(movieId)
                .movieGenre(MovieGenre.HORROR)
                .length(90)
                .title(movieTitle)
                .build();


        var screening = ScreeningEntity.builder()
                .cinemaRoomId(cinemaRoomId)
                .movieId(movieId)
                .dateTime(LocalDateTime.of(2021, 10, 1, 17, 30))
                .build();


        var screeningToInsert = CreateUpdateScreeningDto.builder()
                .cinemaRoomId(cinemaRoomId)
                .createUpdateMovieDto(CreateUpdateMovieDto.builder().title(movieTitle).length(90).build())
                .dateTime(LocalDateTime.of(2021, 10, 1, 18, 30))
                .build();

        when(movieEntityDao.findByTitle(movieTitle))
                .thenReturn(Optional.of(movieFromDb));

        when(movieEntityDao.findById(movieId))
                .thenReturn(Optional.of(movieFromDb));

        when(screeningEntityDao.findAllByCinemaRoomAndDate(any(), any()))
                .thenReturn(List.of(screening));

        assertThatThrownBy(() -> screeningsService.createScreeening(screeningToInsert))
                .isInstanceOf(ScreeningsServiceException.class)
                .hasMessageContaining("cannot add screening - that time is already booked");

    }

    @Test
    @DisplayName("when adding of screening is correct")
    public void test3() {

        var cinemaRoomId = 3L;
        var movieId = 1L;
        var movieTitle = "Andreas Abenteuer";

        var movieFromDb = MovieEntity.builder()
                .id(movieId)
                .movieGenre(MovieGenre.HORROR)
                .length(90)
                .title(movieTitle)
                .build();


        var screening = ScreeningEntity.builder()
                .cinemaRoomId(cinemaRoomId)
                .movieId(movieId)
                .dateTime(LocalDateTime.of(2021, 10, 1, 17, 30))
                .build();


        var createScreeningDto = CreateUpdateScreeningDto.builder()
                .cinemaRoomId(cinemaRoomId)
                .createUpdateMovieDto(CreateUpdateMovieDto.builder().title(movieTitle).length(90).build())
                .dateTime(LocalDateTime.of(2021, 10, 1, 20, 00))
                .build();

        var screeningToInsert = createScreeningDto
                .toScreening()
                .withMovieId(movieId)
                .toEntity();

        when(movieEntityDao.findByTitle(movieTitle))
                .thenReturn(Optional.of(movieFromDb));

        when(movieEntityDao.findById(movieId))
                .thenReturn(Optional.of(movieFromDb));

        when(screeningEntityDao.findAllByCinemaRoomAndDate(any(), any()))
                .thenReturn(List.of(screening));

        when(screeningEntityDao.save(screeningToInsert))
                .thenReturn(Optional.of(screeningToInsert));

        assertThat(screeningsService.createScreeening(createScreeningDto))
                .isEqualTo(screeningToInsert.toScreening().toGetScreeningDto());

    }

    @Test
    @DisplayName("when screening is searched by movie id")
    public void test4() {

        var id = 1L;
        var dateTime = LocalDateTime.now().plusMonths(4);
        var length = 90;
        var city = "Posen";
        var cinemaName = "Ultra kino";
        var houseNumer = "56";
        var movieTitle = "Terminator";
        var street = "Main street";


        var screeningInfo = ScreeningInfo.builder()
                .id(id)
                .dateTime(dateTime)
                .length(length)
                .city(city)
                .cinemaName(cinemaName)
                .houseNumber(houseNumer)
                .movieTitle(movieTitle)
                .street(street)
                .build();

        var expectedScreeningInfoDto = GetScreeningInfoDto.builder()
                .id(id)
                .dateTime(dateTime)
                .length(length)
                .city(city)
                .cinemaName(cinemaName)
                .houseNumber(houseNumer)
                .movieTitle(movieTitle)
                .street(street)
                .build();

        when(screeningInfoDao.findByMovie(1L))
                .thenReturn(List.of(screeningInfo));

        assertThat(screeningsService.findByMovie(1L))
                .isEqualTo(List.of(expectedScreeningInfoDto));
    }

    @Test
    @DisplayName("when screening is searched by cinema")
    public void test5() {

        var id = 1L;
        var dateTime = LocalDateTime.now().plusMonths(4);
        var length = 90;
        var city = "Posen";
        var cinemaName = "Ultra kino";
        var houseNumer = "56";
        var movieTitle = "Terminator";
        var street = "Main street";


        var screeningInfo = ScreeningInfo.builder()
                .id(id)
                .dateTime(dateTime)
                .length(length)
                .city(city)
                .cinemaName(cinemaName)
                .houseNumber(houseNumer)
                .movieTitle(movieTitle)
                .street(street)
                .build();

        var expectedScreeningInfoDto = GetScreeningInfoDto.builder()
                .id(id)
                .dateTime(dateTime)
                .length(length)
                .city(city)
                .cinemaName(cinemaName)
                .houseNumber(houseNumer)
                .movieTitle(movieTitle)
                .street(street)
                .build();

        when(screeningInfoDao.findByCinema(1L))
                .thenReturn(List.of(screeningInfo));

        assertThat(screeningsService.findByCinema(1L))
                .isEqualTo(List.of(expectedScreeningInfoDto));
    }

    @Test
    @DisplayName("when screening is searched by keyword")
    public void test6() {

        var id = 1L;
        var dateTime = LocalDateTime.now().plusMonths(4);
        var length = 90;
        var city = "Posen";
        var cinemaName = "Ultra kino";
        var houseNumer = "56";
        var movieTitle = "Terminator";
        var street = "Main street";


        var screeningInfo = ScreeningInfo.builder()
                .id(id)
                .dateTime(dateTime)
                .length(length)
                .city(city)
                .cinemaName(cinemaName)
                .houseNumber(houseNumer)
                .movieTitle(movieTitle)
                .street(street)
                .build();

        var expectedScreeningInfoDto = GetScreeningInfoDto.builder()
                .id(id)
                .dateTime(dateTime)
                .length(length)
                .city(city)
                .cinemaName(cinemaName)
                .houseNumber(houseNumer)
                .movieTitle(movieTitle)
                .street(street)
                .build();

        when(screeningInfoDao.findByKeyword("Posen"))
                .thenReturn(List.of(screeningInfo));

        assertThat(screeningsService.findByKeyword("Posen"))
                .isEqualTo(List.of(expectedScreeningInfoDto));

    }

    @Test
    @DisplayName("when screening is searched by date")
    public void test7() {

        var id = 1L;
        var dateTime = LocalDateTime.now().plusMonths(4);
        var length = 90;
        var city = "Posen";
        var cinemaName = "Ultra kino";
        var houseNumer = "56";
        var movieTitle = "Terminator";
        var street = "Main street";

        var searchedDate = LocalDate.now().plusMonths(5);


        var screeningInfo = ScreeningInfo.builder()
                .id(id)
                .dateTime(dateTime)
                .length(length)
                .city(city)
                .cinemaName(cinemaName)
                .houseNumber(houseNumer)
                .movieTitle(movieTitle)
                .street(street)
                .build();

        var expectedScreeningInfoDto = GetScreeningInfoDto.builder()
                .id(id)
                .dateTime(dateTime)
                .length(length)
                .city(city)
                .cinemaName(cinemaName)
                .houseNumber(houseNumer)
                .movieTitle(movieTitle)
                .street(street)
                .build();

        when(screeningInfoDao.findByDate(searchedDate))
                .thenReturn(List.of(screeningInfo));

        assertThat(screeningsService.findByDate(searchedDate))
                .isEqualTo(List.of(expectedScreeningInfoDto));
    }

    @Test
    @DisplayName("when screening is updated")
    public void test8() {

        var cinemaRoomId = 3L;
        var movieId = 1L;
        var dateTime = LocalDateTime.now().plusDays(5);
        var movieTitle = "Movie title";

        var screening = ScreeningEntity.builder()
                .cinemaRoomId(cinemaRoomId)
                .movieId(movieId)
                .dateTime(dateTime)
                .build();

        var screeningToUpdate = CreateUpdateScreeningDto.builder()
                .cinemaRoomId(cinemaRoomId)
                .createUpdateMovieDto(CreateUpdateMovieDto.builder().build())
                .dateTime(dateTime)
                .build();

        var expectedScreeningDto = GetScreeningDto.builder()
                .cinemaRoomId(cinemaRoomId)
                .movieId(movieId)
                .dateTime(dateTime)
                .build();


        var movieFromDb = MovieEntity.builder()
                .id(1L)
                .movieGenre(MovieGenre.HORROR)
                .length(90)
                .title(movieTitle)
                .build();

        when(movieEntityDao.findByTitle(any()))
                .thenReturn(Optional.of(movieFromDb));

        when(screeningEntityDao.update(1L, screening))
                .thenReturn(Optional.of(screening));

        assertThat(screeningsService.updateScreening(1L, screeningToUpdate))
                .isEqualTo(expectedScreeningDto);

    }

    @Test
    @DisplayName("when screening is deleted")
    public void test9() {


        var screeningId = 1L;
        var cinemaRoomId = 3L;
        var movieId = 1L;
        var dateTime = LocalDateTime.now().plusDays(5);
        var newDateTime = LocalDateTime.now().plusDays(15);
        var movieTitle = "Movie title";

        var screening = ScreeningEntity.builder()
                .cinemaRoomId(cinemaRoomId)
                .movieId(movieId)
                .dateTime(dateTime)
                .build();


        var expectedScreeningDto = GetScreeningDto.builder()
                .cinemaRoomId(cinemaRoomId)
                .movieId(movieId)
                .dateTime(dateTime)
                .build();

        when(screeningEntityDao.deleteById(screeningId))
                .thenReturn(Optional.of(screening));

        assertThat(screeningsService.deleteScreening(screeningId))
                .isEqualTo(expectedScreeningDto);

    }
}
