package com.cinema.app.domain.address.dto;

import com.cinema.app.domain.address.Address;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateUpdateAddressDtoTest {


    @Test
    @DisplayName("when conversion to address is successfully")
    public void test1() {

        var city = "Berlin";
        var street = "Kurzweg";
        var houseNumber = "32/5";
        var zipCode = "62-200";

        var createAddressDto = CreateUpdateAddressDto.builder()
                .city(city)
                .street(street)
                .zipCode(zipCode)
                .houseNumber(houseNumber)
                .build();

        var address = createAddressDto.toAddress();

        var expectedAddress = Address.builder()
                .city(city)
                .street(street)
                .zipCode(zipCode)
                .houseNumber(houseNumber)
                .build();

        Assertions.assertThat(address)
                .isEqualTo(expectedAddress);
    }


}
