package com.cinema.app.domain.screening;

import com.cinema.app.domain.screening.dto.GetScreeningDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Setter
@ToString

/**
 * class representing screening in cinema room
 * @author Szymon Sawicki
 */

public class Screening {

    Long id;
    Long movieId;
    Long cinemaRoomId;
    LocalDateTime dateTime;

    public GetScreeningDto toGetScreeningDto() {
        return GetScreeningDto.builder()
                .id(id)
                .movieId(movieId)
                .cinemaRoomId(cinemaRoomId)
                .dateTime(dateTime)
                .build();
    }

}
