package com.cinema.app.domain.screening;

import com.cinema.app.domain.screening.dto.GetScreeningDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode

/**
 * class representing screening in cinema room
 * @author Szymon Sawicki
 */

public class Screening {

    Long id;
    Long movieId;
    Long cinemaRoomId;
    LocalDate date;
    LocalTime time;

    public GetScreeningDto toGetScreeningDto() {
        return GetScreeningDto.builder()
                .id(id)
                .movieId(movieId)
                .cinemaRoomId(cinemaRoomId)
                .date(date)
                .time(time)
                .build();
    }

}
