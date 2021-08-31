package com.cinema.app.domain.screening;

import com.cinema.app.domain.screening.dto.GetScreeningDto;
import com.cinema.app.infrastructure.persistence.entity.ScreeningEntity;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
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

    public Screening withMovieId(Long newMovieId) {
        return Screening.builder()
                .id(id)
                .movieId(newMovieId)
                .cinemaRoomId(cinemaRoomId)
                .dateTime(dateTime)
                .build();
    }

    public ScreeningEntity toEntity() {
        return ScreeningEntity.builder()
                .id(id)
                .movieId(movieId)
                .cinemaRoomId(cinemaRoomId)
                .dateTime(dateTime)
                .build();
    }

}
