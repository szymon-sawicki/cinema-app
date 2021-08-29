package com.cinema.app.domain.screening;

import com.cinema.app.domain.screening.dto.GetScreeningDto;
import com.cinema.app.infrastructure.persistence.entity.ScreeningEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

public class ScreeningDomainTest {

    @Test
    @DisplayName("when conversion to get screening dto is correct")
    public void test1() {

        var id = 2L;
        var movieId = 3L;
        var cinemaRoomId = 6L;
        var dateTime = LocalDateTime.now();

        var screening = Screening.builder()
                .id(id)
                .movieId(movieId)
                .cinemaRoomId(cinemaRoomId)
                .dateTime(dateTime)
                .build();

        var getScreeningDto = screening.toGetScreeningDto();

        var expectedGetScreeningDto = GetScreeningDto.builder()
                .id(id)
                .movieId(movieId)
                .cinemaRoomId(cinemaRoomId)
                .dateTime(dateTime)
                .build();
    }

    @Test
    @DisplayName("when conversion to entity is correct")
    public void test2() {

        var id = 2L;
        var movieId = 3L;
        var cinemaRoomId = 6L;
        var dateTime = LocalDateTime.now();

        var screening = Screening.builder()
                .id(id)
                .movieId(movieId)
                .cinemaRoomId(cinemaRoomId)
                .dateTime(dateTime)
                .build();

        var screeningEntity = ScreeningEntity.builder()
                .id(id)
                .movieId(movieId)
                .cinemaRoomId(cinemaRoomId)
                .dateTime(dateTime)
                .build();

        assertThat(screening.toEntity())
                .isEqualTo(screeningEntity);

    }

}
