package com.cinema.app.domain.cinema;

import com.cinema.app.domain.address.Address;
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
    Address address;

}
