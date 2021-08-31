package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.MoviesServiceException;
import com.cinema.app.domain.movie.Movie;
import com.cinema.app.domain.movie.dto.CreateMovieDto;
import com.cinema.app.domain.movie.type.MovieGenre;
import com.cinema.app.infrastructure.persistence.dao.MovieEntityDao;
import com.cinema.app.infrastructure.persistence.entity.MovieEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MoviesServiceTest {

    @Mock
    private MovieEntityDao movieEntityDao;

    @InjectMocks
    private MoviesService moviesService;

    @Test
    @DisplayName("when movie with given title already exists")
    public void test1() {

        var title = "Greys Anatomy";

        var movie = CreateMovieDto.builder()
                .title(title)
                .length(78)
                .movieGenre(MovieGenre.ACTION)
                .premiereDate(LocalDate.now().minusYears(5))
                .build();


        when(movieEntityDao.findByTitle(title))
                .thenReturn(Optional.of(MovieEntity.builder().title(title).build()));

        Assertions.assertThatThrownBy(() -> moviesService.addMovie(movie))
                .isInstanceOf(MoviesServiceException.class)
                .hasMessageContaining("is already present in database");

    }

    @Test
    @DisplayName("when adding movie is correct")
    public void test2() {

        var title = "New movie";
        var length = 23;
        var movieGenre = MovieGenre.ACTION;
        var premiereDate = LocalDate.now().minusYears(3);

        var createMovieDto = CreateMovieDto.builder()
                .title(title)
                .length(length)
                .movieGenre(movieGenre)
                .premiereDate(premiereDate)
                .build();

        var movie = Movie.builder()
                .title(title)
                .length(length)
                .premiereDate(premiereDate)
                .movieGenre(movieGenre)
                .build();

        when(movieEntityDao.save(any()))
                .thenReturn(Optional.of(movie.toEntity()));

        assertThat(moviesService.addMovie(createMovieDto))
                .isEqualTo(movie.toGetMovieDto());

    }


    @Test
    @DisplayName("when title of movie is null")
    public void test3() {

        assertThatThrownBy(() -> moviesService.findByTitle(null))
                .isInstanceOf(MoviesServiceException.class)
                .hasMessageContaining("title is null");

    }

    @Test
    @DisplayName("when title of movie to search have wrong format")
    public void test4() {

        var name = "&^ms ddd";

        assertThatThrownBy(() -> moviesService.findByTitle(name))
                .isInstanceOf(MoviesServiceException.class)
                .hasMessageContaining("title have wrong format");

    }

    @Test
    @DisplayName("when movie cannot be found")
    public void test5() {

        var title = "Lord of the Rings";

        when(movieEntityDao.findByTitle(title))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> moviesService.findByTitle(title))
                .isInstanceOf(MoviesServiceException.class)
                .hasMessageContaining("cannot find movie");
    }

    @Test
    @DisplayName("when movie is found by title")
    public void test6() {

        var title = "New movie";
        var length = 23;
        var movieGenre = MovieGenre.ACTION;
        var premiereDate = LocalDate.now().minusYears(3);

        var movie = Movie.builder()
                .title(title)
                .length(length)
                .movieGenre(movieGenre)
                .premiereDate(premiereDate)
                .build();

        when(movieEntityDao.findByTitle(title))
                .thenReturn(Optional.of(movie.toEntity()));

        assertThat(moviesService.findByTitle(title))
                .isEqualTo(movie.toGetMovieDto());

    }

    @Test
    @DisplayName("when cinemas are searched by genre, should return list with 2 items")
    public void test7() {

        var movie1 = Movie.builder()
                .title("Terminator")
                .length(92)
                .movieGenre(MovieGenre.HORROR)
                .premiereDate(LocalDate.now().minusYears(2))
                .build();

        var movie2 = Movie.builder()
                .title("Lord of the Rings")
                .length(92)
                .movieGenre(MovieGenre.HORROR)
                .premiereDate(LocalDate.now().minusYears(2))
                .build();

        var listWithGetMovieDtos = List.of(movie1.toGetMovieDto(), movie2.toGetMovieDto());

        when(movieEntityDao.findMoviesByGenre(MovieGenre.HORROR))
                .thenReturn(List.of(movie1.toEntity(), movie2.toEntity()));

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
