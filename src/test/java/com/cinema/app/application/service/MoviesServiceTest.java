package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.MoviesServiceException;
import com.cinema.app.domain.movie.Movie;
import com.cinema.app.domain.movie.dto.CreateMovieDto;
import com.cinema.app.domain.movie.type.MovieGenre;
import com.cinema.app.infrastructure.persistence.MovieDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class MoviesServiceTest {

    @Mock
    private MovieDao movieDao;

    @InjectMocks
    private MoviesService moviesService;

    @Test
    @DisplayName("when movie with given name already exists")
    public void test1() {

        var name = "Greys Anatomy";


        when(movieDao.findByName(name))
                .thenReturn(Optional.of(Movie.builder().name(name).build()));

        System.out.println("--------------------------------------------------");
        System.out.println("--------------------------------------------------");
        System.out.println( moviesService.findByName(name));
        System.out.println("--------------------------------------------------");
        System.out.println("--------------------------------------------------");

        Assertions.assertThatThrownBy(() -> moviesService.findByName(name))
                .isInstanceOf(MoviesServiceException.class)
                .hasMessageContaining("is already present in database");

    }

    @Test
    @DisplayName("when adding movie is correct")
    public void test2() {

        var name = "New movie";
        var length = 23;
        var movieGenre = MovieGenre.ACTION;
        var premiereDate = LocalDate.now().minusYears(3);

        var createMovieDto = CreateMovieDto.builder()
                .name(name)
                .length(length)
                .movieGenre(movieGenre)
                .premiereDate(premiereDate)
                .build();

        var movie = Movie.builder()
                .name(name)
                .length(length)
                .premiereDate(premiereDate)
                .movieGenre(movieGenre)
                .build();

        when(movieDao.save(createMovieDto.toMovie()))
                .thenReturn(Optional.of(movie));

        assertThat(moviesService.addMovie(createMovieDto))
                .isEqualTo(movie.toGetMovieDto());

    }


    @Test
    @DisplayName("when name of movie is null")
    public void test3() {

        assertThatThrownBy(() -> moviesService.findByName(null))
                .isInstanceOf(MoviesServiceException.class)
                .hasMessageContaining("name is null");

    }

    @Test
    @DisplayName("when name of movie to search have wrong format")
    public void test4() {

        var name = "&^ms ddd";

        assertThatThrownBy(() -> moviesService.findByName(name))
                .isInstanceOf(MoviesServiceException.class)
                .hasMessageContaining("name have wrong format");

    }

    @Test
    @DisplayName("when movie cannot be found")
    public void test5() {

        var name = "Lord of the Rings";

        when(movieDao.findByName(name))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> moviesService.findByName(name))
                .isInstanceOf(MoviesServiceException.class)
                .hasMessageContaining("cannot find movie");
    }

    @Test
    @DisplayName("when movie is found by name")
    public void test6() {

        var name = "New movie";
        var length = 23;
        var movieGenre = MovieGenre.ACTION;
        var premiereDate = LocalDate.now().minusYears(3);

        var movie = Movie.builder()
                .name(name)
                .length(length)
                .movieGenre(movieGenre)
                .premiereDate(premiereDate)
                .build();

        when(movieDao.findByName(name))
                .thenReturn(Optional.of(movie));

        assertThat(moviesService.findByName(name))
                .isEqualTo(movie.toGetMovieDto());

    }

    @Test
    @DisplayName("when cinemas are searched by genre, should return list with 2 items")
    public void test7() {

        var movie1 = Movie.builder()
                .name("Terminator")
                .length(92)
                .movieGenre(MovieGenre.HORROR)
                .premiereDate(LocalDate.now().minusYears(2))
                .build();

        var movie2 = Movie.builder()
                .name("Lord of the Rings")
                .length(92)
                .movieGenre(MovieGenre.HORROR)
                .premiereDate(LocalDate.now().minusYears(2))
                .build();

        var listWithGetMovieDtos = List.of(movie1.toGetMovieDto(),movie2.toGetMovieDto());

        when(movieDao.findMoviesByGenre(MovieGenre.HORROR))
                .thenReturn(List.of(movie1,movie2));

        assertThat(moviesService.findByGenre(MovieGenre.HORROR))
                .isEqualTo(listWithGetMovieDtos);

    }

    @Test
    @DisplayName("when genre is null")
    public void test8() {

        assertThatThrownBy(() -> moviesService.findByGenre(null))
                .isInstanceOf(MoviesServiceException.class)
                .hasMessageContaining("genre is null");

    }

}
