package com.cinema.app.domain.screening.dto;

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

public class GetScreeningDto {

    Long id;
    Long movieId;
    Long cinemaRoomId;
    LocalDateTime dateTime;

}
