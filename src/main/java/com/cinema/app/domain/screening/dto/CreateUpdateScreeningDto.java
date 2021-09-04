package com.cinema.app.domain.screening.dto;

import com.cinema.app.domain.movie.dto.CreateUpdateMovieDto;
import com.cinema.app.domain.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateUpdateScreeningDto {

    CreateUpdateMovieDto createUpdateMovieDto;
    Long cinemaRoomId;
    LocalDateTime dateTime;

    public Screening toScreening() {
        return Screening.builder()
                .cinemaRoomId(cinemaRoomId)
                .dateTime(dateTime)
                .build();

    }

}
