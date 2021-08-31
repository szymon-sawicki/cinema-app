package com.cinema.app.domain.screening.dto;

import com.cinema.app.domain.cinema_room.dto.GetCinemaRoomDto;
import com.cinema.app.domain.movie.MovieUtils;
import com.cinema.app.domain.movie.dto.CreateMovieDto;
import com.cinema.app.domain.movie.dto.GetMovieDto;
import com.cinema.app.domain.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateScreeningDto {

    CreateMovieDto createMovieDto;
    Long cinemaRoomId;
    LocalDateTime dateTime;

    public Screening toScreening() {
        return Screening.builder()
                .cinemaRoomId(cinemaRoomId)
                .dateTime(dateTime)
                .build();

    }

}
