package com.cinema.app.domain.movie.dto;

import com.cinema.app.domain.movie.Movie;
import com.cinema.app.domain.movie.type.MovieGenre;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CreateMovieDto {

    String name;
    MovieGenre movieGenre;
    LocalDate premiereDate;
    Integer length;

    public Movie toMovie() {
        return Movie.builder()
                .name(name)
                .movieGenre(movieGenre)
                .premiereDate(premiereDate)
                .length(length)
                .build();
    }
}