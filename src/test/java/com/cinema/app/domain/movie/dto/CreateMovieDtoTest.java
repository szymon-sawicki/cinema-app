package com.cinema.app.domain.movie.dto;

import com.cinema.app.domain.movie.Movie;
import com.cinema.app.domain.movie.type.MovieGenre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CreateMovieDtoTest {

    @Test
    @DisplayName("when conversion to movie is correct")
    public void test1() {

        var id = 1L;
        var name = "Birds";
        var movieGenre = MovieGenre.HORROR;
        var premiereDate = LocalDate.of(2000,12,21);
        var length = 90;

        var createMovieDto = CreateMovieDto.builder()
                .name(name)
                .movieGenre(movieGenre)
                .premiereDate(premiereDate)
                .length(length)
                .build();

        var movie = createMovieDto.toMovie();

        var expectedMovie = Movie.builder()
                .name(name)
                .movieGenre(movieGenre)
                .premiereDate(premiereDate)
                .length(length)
                .build();

    }

}
