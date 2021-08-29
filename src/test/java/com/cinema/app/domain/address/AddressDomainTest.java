package com.cinema.app.domain.address;

import com.cinema.app.domain.address.dto.GetAddressDto;
import com.cinema.app.infrastructure.persistence.entity.AddressEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AddressDomainTest {

    @Test
    @DisplayName("when convertion to GetAddressDto is correct")
    public void test1() {

        var city = "Berlin";
        var street = "Kurzweg";
        var houseNumber = "32/5";
        var zipCode = "62-200";

        var address = Address.builder()
                .city(city)
                .street(street)
                .zipCode(zipCode)
                .houseNumber(houseNumber)
                .build();

        var expectedAddress = GetAddressDto.builder()
                .city(city)
                .street(street)
                .zipCode(zipCode)
                .houseNumber(houseNumber)
                .build();

        var getAddressDto = address.toGetAddressDto();

        Assertions.assertThat(getAddressDto).isEqualTo(expectedAddress);
    }

    @Test
    @DisplayName("when conversion to entity is correct")
    public void test2() {

        var city = "Berlin";
        var street = "Kurzweg";
        var houseNumber = "32/5";
        var zipCode = "62-200";

        var address = Address.builder()
                .id(1L)
                .city(city)
                .street(street)
                .zipCode(zipCode)
                .houseNumber(houseNumber)
                .build();

        var addressEntity = AddressEntity.builder()
                .id(1L)
                .city(city)
                .street(street)
                .zipCode(zipCode)
                .houseNumber(houseNumber)
                .build();

        Assertions.assertThat(address.toEntity())
                .isEqualTo(addressEntity);

    }
}
