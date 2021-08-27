package com.cinema.app.domain.screening;

import com.cinema.app.domain.screening.dto.GetScreeningDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

}
