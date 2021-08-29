package com.cinema.app.domain.movie;


import com.cinema.app.domain.movie.dto.GetMovieDto;
import com.cinema.app.domain.movie.type.MovieGenre;
import com.cinema.app.infrastructure.persistence.entity.MovieEntity;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString

/**
 * class representing movie
 * @author Szymon Sawicki
 */

public class Movie {

    Long id;
    String title;
    MovieGenre movieGenre;
    LocalDate premiereDate;
    Integer length;

    public GetMovieDto toGetMovieDto() {
        return GetMovieDto.builder()
                .id(id)
                .title(title)
                .movieGenre(movieGenre)
                .premiereDate(premiereDate)
                .length(length)
                .build();
    }

    public MovieEntity toEntity() {
        return MovieEntity.builder()
                .id(id)
                .title(title)
                .movieGenre(movieGenre)
                .premiereDate(premiereDate)
                .length(length)
                .build();
    }

}
