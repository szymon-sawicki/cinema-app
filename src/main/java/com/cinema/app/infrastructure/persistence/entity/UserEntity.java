package com.cinema.app.infrastructure.persistence.entity;

import com.cinema.app.domain.user.User;
import com.cinema.app.domain.user.type.Gender;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@EqualsAndHashCode

public class UserEntity {

    Long id;
    String username;
    String password;
    String mail;
    String name;
    LocalDate birthDate;
    Gender gender;

    public User toUser() {
        return User.builder()
                .id(id)
                .username(username)
                .mail(mail)
                .name(name)
                .birthDate(birthDate)
                .gender(gender)
                .build();
    }


}
