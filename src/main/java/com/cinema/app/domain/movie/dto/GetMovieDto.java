package com.cinema.app.domain.movie.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder

public class GetMovieDto {

    Long id;
    String name;
    MovieGenre movieGenre;
    LocalDate premiereDate;
    Integer length;

}
