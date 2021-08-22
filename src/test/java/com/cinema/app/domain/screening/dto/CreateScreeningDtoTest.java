package com.cinema.app.domain.screening.dto;

import com.cinema.app.domain.movie.dto.GetMovieDto;
import com.cinema.app.domain.screening.Screening;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

public class CreateScreeningDtoTest {

    @Test
    @DisplayName("when conversion to screening is correct")
    public void test1() {
        var id = 2L;
        var movieId = 5L;
        var cinemaRoomId = 6L;
        var date = LocalDate.now();
        var time = LocalTime.now();

        var createScreeningDto = CreateScreeningDto.builder()
                .id(id)
                .getMovieDto(GetMovieDto.builder().id(5L).build())
                .cinemaRoomId(cinemaRoomId)
                .date(date)
                .time(time)
                .build();

        var screening = createScreeningDto.toScreening();

        var expectedScreening = Screening.builder()
                .id(id)
                .movieId(movieId)
                .cinemaRoomId(cinemaRoomId)
                .date(date)
                .time(time)
                .build();

        assertThat(screening)
                .isEqualTo(screening);

    }
}