package com.cinema.app.screening.dto;

import com.cinema.app.infrastructure.persistence.entity.view.ScreeningInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GetScreeningInfoDto {

    Long id;
    String cinemaName;
    String street;
    String houseNumber;
    String city;
    String cinemaRoomName;
    String movieTitle;
    Integer length;
    LocalDateTime dateTime;

    public ScreeningInfo toScreeningInfo() {

        return ScreeningInfo.builder()
                .id(id)
                .cinemaName(cinemaName)
                .street(street)
                .houseNumber(houseNumber)
                .city(city)
                .cinemaRoomName(cinemaRoomName)
                .movieTitle(movieTitle)
                .length(length)
                .dateTime(dateTime)
                .build();

    }
}
