package com.cinema.app.infrastructure.persistence.entity.view;

import com.cinema.app.domain.screening.dto.GetScreeningInfoDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@ToString
@EqualsAndHashCode

public class ScreeningInfo {

    Long id;
    String cinemaName;
    String street;
    String houseNumber;
    String city;
    String cinemaRoomName;
    String movieTitle;
    Integer length;
    LocalDateTime dateTime;

    public GetScreeningInfoDto toGetScreeningInfoDto() {
        return GetScreeningInfoDto.builder()
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
