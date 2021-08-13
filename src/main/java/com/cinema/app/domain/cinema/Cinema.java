package com.cinema.app.domain.cinema;

import com.cinema.app.domain.address.Address;
import com.cinema.app.domain.cinema.dto.GetCinemaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode

/**
 * class representing cinema objects
 * @author Szymon Sawicki
 */

public class Cinema {

    Long id;
    String name;
    Long addressId;

    public Cinema withAddress(Long newAddressId) {
        return Cinema
                .builder()
                .id(id)
                .name(name)
                .addressId(newAddressId)
                .build();
    }

    public GetCinemaDto toGetCinemaDto() {
        return GetCinemaDto.builder()
                .id(id)
                .name(name)
                .addressId(addressId)
                .build();
    }

}
