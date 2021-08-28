package com.cinema.app.domain.movie;

import com.cinema.app.domain.movie.dto.GetMovieDto;
import com.cinema.app.domain.movie.type.MovieGenre;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class MovieDomainTest {

    @Test
    @DisplayName("when conversion to get movie dto is correct")
    public void test1() {

        var id = 1L;
        var name = "Birds";
        var movieGenre = MovieGenre.HORROR;
        var premiereDate = LocalDate.of(2000,12,21);
        var length = 90;


        var movie = Movie.builder()
                .id(id)
                .title(name)
                .movieGenre(movieGenre)
                .premiereDate(premiereDate)
                .length(length)
                .build();

        var getMovieDto = movie.toGetMovieDto();

        var expectedGetMovieDto = GetMovieDto.builder()
                .id(id)
                .name(name)
                .movieGenre(movieGenre)
                .premiereDate(premiereDate)
                .length(length)
                .build();

        Assertions.assertThat(getMovieDto)
                .isEqualTo(expectedGetMovieDto);

    }

}
