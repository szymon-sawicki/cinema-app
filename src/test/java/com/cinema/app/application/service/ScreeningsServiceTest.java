package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.ScreeningsServiceException;
import com.cinema.app.domain.movie.dto.CreateMovieDto;
import com.cinema.app.domain.movie.type.MovieGenre;
import com.cinema.app.domain.screening.dto.CreateScreeningDto;
import com.cinema.app.infrastructure.persistence.dao.MovieEntityDao;
import com.cinema.app.infrastructure.persistence.dao.ScreeningEntityDao;
import com.cinema.app.infrastructure.persistence.entity.MovieEntity;
import com.cinema.app.infrastructure.persistence.entity.ScreeningEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

public class ScreeningsServiceTest {

    @Mock
    private ScreeningEntityDao screeningEntityDao;

    @Mock
    private MovieEntityDao movieEntityDao;

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
                .dateTime(LocalDateTime.of(2021,10,1,19,30))
                .build();


        var screeningToInsert = CreateScreeningDto.builder()
                .cinemaRoomId(cinemaRoomId)
                .createMovieDto(CreateMovieDto.builder().title(movieTitle).length(90).build())
                .dateTime(LocalDateTime.of(2021,10,1,18,30))
                .build();

        when(movieEntityDao.findByTitle(movieTitle))
                .thenReturn(Optional.of(movieFromDb));

        when(movieEntityDao.findById(movieId))
                .thenReturn(Optional.of(movieFromDb));

        when(screeningEntityDao.findAllByCinemaRoomAndDate(any(), any()))
                .thenReturn(List.of(screening));

        Assertions.assertThatThrownBy(() -> screeningsService.createScreeening(screeningToInsert))
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
                .dateTime(LocalDateTime.of(2021,10,1,17,30))
                .build();


        var screeningToInsert = CreateScreeningDto.builder()
                .cinemaRoomId(cinemaRoomId)
                .createMovieDto(CreateMovieDto.builder().title(movieTitle).length(90).build())
                .dateTime(LocalDateTime.of(2021,10,1,18,30))
                .build();

        when(movieEntityDao.findByTitle(movieTitle))
                .thenReturn(Optional.of(movieFromDb));

        when(movieEntityDao.findById(movieId))
                .thenReturn(Optional.of(movieFromDb));

        when(screeningEntityDao.findAllByCinemaRoomAndDate(any(), any()))
                .thenReturn(List.of(screening));

        Assertions.assertThatThrownBy(() -> screeningsService.createScreeening(screeningToInsert))
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
                .dateTime(LocalDateTime.of(2021,10,1,17,30))
                .build();


        var createScreeningDto = CreateScreeningDto.builder()
                .cinemaRoomId(cinemaRoomId)
                .createMovieDto(CreateMovieDto.builder().title(movieTitle).length(90).build())
                .dateTime(LocalDateTime.of(2021,10,1,20,00))
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

        Assertions.assertThat(screeningsService.createScreeening(createScreeningDto))
                .isEqualTo(screeningToInsert.toScreening().toGetScreeningDto());

    }
}
