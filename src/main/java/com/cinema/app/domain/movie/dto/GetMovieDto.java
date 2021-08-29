package com.cinema.app.domain.movie.dto;

import com.cinema.app.domain.movie.type.MovieGenre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GetMovieDto {

    Long id;
    String title;
    MovieGenre movieGenre;
    LocalDate premiereDate;
    Integer length;

}
