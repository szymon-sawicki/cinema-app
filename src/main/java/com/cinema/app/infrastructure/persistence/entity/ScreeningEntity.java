package com.cinema.app.infrastructure.persistence.entity;

import com.cinema.app.domain.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter

public class ScreeningEntity {
    private Long id;
    private Long movieId;
    private Long cinemaRoomId;
    private LocalDateTime dateTime;

    public Screening toScreening() {
        return Screening.builder()
                .id(id)
                .movieId(movieId)
                .cinemaRoomId(cinemaRoomId)
                .dateTime(dateTime)
                .build();
    }
}
