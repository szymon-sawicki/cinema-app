package com.cinema.app.domain.user;

import com.cinema.app.domain.user.dto.GetUserDto;
import com.cinema.app.domain.user.type.Gender;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class UserDomainTest {

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

        var user = User.builder()
                .id(id)
                .username(username)
                .mail(mail)
                .name(name)
                .birthDate(birthDate)
                .creationDate(creationDate)
                .gender(gender)
                .build();


        var getUserDto = GetUserDto.builder()
                .id(id)
                .username(username)
                .mail(mail)
                .name(name)
                .birthDate(birthDate)
                .creationDate(creationDate)
                .gender(gender)
                .build();

        assertThat(user.toGetUserDto())
                .isEqualTo(getUserDto);
    }
}
