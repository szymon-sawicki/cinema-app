package com.cinema.app.domain.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode

/**
 *  class representing address of cinema
 * @Author Szymon Sawicki
 */

public class Address {


    Long id;
    String street;
    String houseNumber;
    String city;
    String zipCode;

}
