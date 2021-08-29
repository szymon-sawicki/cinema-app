package com.cinema.app.infrastructure.persistence.entity;

import com.cinema.app.domain.movie.Movie;
import com.cinema.app.domain.movie.type.MovieGenre;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@EqualsAndHashCode

public class MovieEntity {
    private Long id;
    private String title;
    private MovieGenre movieGenre;
    private LocalDate premiereDate;
    private Integer length;

    public Movie toMovie() {
        return Movie.builder()
                .id(id)
                .title(title)
                .movieGenre(movieGenre)
                .premiereDate(premiereDate)
                .length(length)
                .build();
    }

}
