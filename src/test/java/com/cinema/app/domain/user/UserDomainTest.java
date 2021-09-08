package com.cinema.app.domain.user;

import com.cinema.app.domain.user.dto.CreateUserResponseDto;
import com.cinema.app.domain.user.dto.GetUserDto;
import com.cinema.app.domain.user.type.Gender;
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

    @Test
    @DisplayName("when user with today as creation date is returned")
    public void test2() {

        var id = 3L;
        var username = "Leon";
        var mail = "leon@okomo.com";
        var name = "Leon Harris";
        var birthDate = LocalDate.now().minusYears(30);
        var gender = Gender.MALE;
        var today = LocalDate.now();

        var user = User.builder()
                .id(id)
                .username(username)
                .mail(mail)
                .name(name)
                .birthDate(birthDate)
                .gender(gender)
                .build();


        var expectedUser = User.builder()
                .id(id)
                .username(username)
                .mail(mail)
                .name(name)
                .birthDate(birthDate)
                .creationDate(today)
                .gender(gender)
                .build();


        assertThat(user.withCreationDate(today))
                .isEqualTo(expectedUser);
    }

    @Test
    @DisplayName("when new password is given")
    public void test3() {
        var id = 3L;
        var username = "Leon";
        var mail = "leon@okomo.com";
        var name = "Leon Harris";
        var password = "elcjdc";
        var newPassword = "kcsdnlc";
        var birthDate = LocalDate.now().minusYears(30);
        var gender = Gender.MALE;
        var today = LocalDate.now();

        var user = User.builder()
                .id(id)
                .username(username)
                .mail(mail)
                .name(name)
                .password(password)
                .creationDate(today)
                .birthDate(birthDate)
                .gender(gender)
                .build();


        var expectedUser = User.builder()
                .id(id)
                .username(username)
                .mail(mail)
                .name(name)
                .password(newPassword)
                .birthDate(birthDate)
                .creationDate(today)
                .gender(gender)
                .build();

        assertThat(user.withPassword(newPassword))
                .isEqualTo(expectedUser);
    }

    @Test
    @DisplayName("when coversion to CreateUserResponseDto is correct")
    public void test4() {
        var id = 3L;
        var username = "Leon";
        var mail = "leon@okomo.com";
        var name = "Leon Harris";
        var password = "elcjdc";
        var newPassword = "kcsdnlc";
        var birthDate = LocalDate.now().minusYears(30);
        var gender = Gender.MALE;
        var today = LocalDate.now();

        var user = User.builder()
                .id(id)
                .username(username)
                .mail(mail)
                .name(name)
                .password(password)
                .creationDate(today)
                .birthDate(birthDate)
                .gender(gender)
                .build();

        var expectedDto = CreateUserResponseDto.builder()
                .id(id)
                .username(username)
                .build();

        assertThat(user.toCreateUserResponseDto())
                .isEqualTo(expectedDto);
    }
}
