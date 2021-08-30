package com.cinema.app.domain.user.dto;

import com.cinema.app.domain.user.User;
import com.cinema.app.domain.user.type.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserDtoTest {

    @Test
    @DisplayName("when conversion to get user dto is correct")
    public void test1() {

        var id = 3L;
        var username = "Leon";
        var mail = "leon@okomo.com";
        var name = "Leon Harris";
        var birthDate = LocalDate.now().minusYears(30);
        var gender = Gender.MALE;
        var creationDate = LocalDate.now().minusMonths(7);

        var createUserDto = CreateUserDto.builder()
                .username(username)
                .mail(mail)
                .name(name)
                .birthDate(birthDate)
                .gender(gender)
                .build();


        var expectedUser = User.builder()
                .username(username)
                .mail(mail)
                .name(name)
                .birthDate(birthDate)
                .gender(gender)
                .build();

        assertThat(createUserDto.toUser())
                .isEqualTo(expectedUser);
    }

}
