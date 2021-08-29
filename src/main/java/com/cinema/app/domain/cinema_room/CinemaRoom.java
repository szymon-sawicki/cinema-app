package com.cinema.app.domain.cinema_room;

import com.cinema.app.domain.cinema_room.dto.GetCinemaRoomDto;
import com.cinema.app.infrastructure.persistence.entity.CinemaRoomEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode

/**
 * class representing one room of the cinema
 * @author Szymon Sawicki
 */

public class CinemaRoom {

    Long id;
    String name;
    Integer rowsNum;
    Integer placeNumber;
    Long cinemaId;

    public CinemaRoom withCinemaId(Long newCinemaId) {
        return CinemaRoom
                .builder()
                .id(id)
                .name(name)
                .rowsNum(rowsNum)
                .placeNumber(placeNumber)
                .cinemaId(newCinemaId)
                .build();
    }


    public GetCinemaRoomDto toGetCinemaRoomDto() {
        return GetCinemaRoomDto.builder()
                .id(id)
                .name(name)
                .rowsNum(rowsNum)
                .placeNumber(placeNumber)
                .cinemaId(cinemaId)
                .build();
    }

    public CinemaRoomEntity toEntity() {
        return CinemaRoomEntity.builder()
                .name(name)
                .rowsNum(rowsNum)
                .placeNumber(placeNumber)
                .cinemaId(cinemaId)
                .build();
    }

}
