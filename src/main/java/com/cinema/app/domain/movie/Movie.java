package com.cinema.app.domain.movie;


import com.cinema.app.domain.movie.dto.GetMovieDto;
import com.cinema.app.domain.movie.type.MovieGenre;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Setter

/**
 * class representing movie
 * @author Szymon Sawicki
 */

public class Movie {

    Long id;
    String name;
    MovieGenre movieGenre;
    LocalDate premiereDate;
    Integer length;

    public GetMovieDto toGetMovieDto() {
        return GetMovieDto.builder()
                .id(id)
                .name(name)
                .movieGenre(movieGenre)
                .premiereDate(premiereDate)
                .length(length)
                .build();
    }

}
