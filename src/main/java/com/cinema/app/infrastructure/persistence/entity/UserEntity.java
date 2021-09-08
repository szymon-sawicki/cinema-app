package com.cinema.app.infrastructure.persistence.entity;

import com.cinema.app.domain.user.User;
import com.cinema.app.domain.user.type.Gender;
import com.cinema.app.domain.user.type.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@EqualsAndHashCode
@ToString

public class UserEntity {

    Long id;
    String username;
    String password;
    String mail;
    String name;
    Role role;
    LocalDate birthDate;
    LocalDate creationDate;
    Gender gender;

    public User toUser() {
        return User.builder()
                .id(id)
                .username(username)
                .mail(mail)
                .name(name)
                .birthDate(birthDate)
                .role(role)
                .password(password)
                .creationDate(creationDate)
                .gender(gender)
                .build();
    }


}
