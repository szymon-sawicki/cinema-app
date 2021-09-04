package com.cinema.app.domain.screening.dto;

import com.cinema.app.domain.movie.dto.CreateUpdateMovieDto;
import com.cinema.app.domain.screening.Screening;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class CreateUpdateScreeningDtoTest {

    @Test
    @DisplayName("when conversion to screening is correct")
    public void test1() {

        var cinemaRoomId = 6L;
        var dateTime = LocalDateTime.now();

        var createScreeningDto = CreateUpdateScreeningDto.builder()
                .createUpdateMovieDto(CreateUpdateMovieDto.builder().build())
                .cinemaRoomId(cinemaRoomId)
                .dateTime(dateTime)
                .build();

        var screening = createScreeningDto.toScreening();

        var expectedScreening = Screening.builder()
                .cinemaRoomId(cinemaRoomId)
                .dateTime(dateTime)
                .build();

        assertThat(screening)
                .isEqualTo(expectedScreening);

    }
}
