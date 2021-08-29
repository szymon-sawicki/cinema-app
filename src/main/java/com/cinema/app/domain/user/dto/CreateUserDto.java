package com.cinema.app.domain.user.dto;

import com.cinema.app.domain.user.User;
import com.cinema.app.domain.user.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CreateUserDto {

    String username;
    String password;
    String passwordConfirmation;
    String mail;
    String name;
    LocalDate birthDate;
    Gender gender;

    public User toUser() {
        return User.builder()
                .username(username)
                .password(password)
                .mail(mail)
                .name(name)
                .birthDate(birthDate)
                .gender(gender)
                .build();
    }

}
