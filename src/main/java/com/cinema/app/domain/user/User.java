package com.cinema.app.domain.user;

import com.cinema.app.domain.user.dto.GetUserDto;
import com.cinema.app.domain.user.type.Gender;
import com.cinema.app.infrastructure.persistence.entity.UserEntity;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString

public class User {

    Long id;
    String username;
    String password;
    String mail;
    String name;
    LocalDate birthDate;
    LocalDate creationDate;
    Gender gender;

    public GetUserDto toGetUserDto() {
        return GetUserDto.builder()
                .id(id)
                .username(username)
                .mail(mail)
                .name(name)
                .birthDate(birthDate)
                .creationDate(creationDate)
                .gender(gender)
                .build();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(id)
                .username(username)
                .mail(mail)
                .name(name)
                .birthDate(birthDate)
                .creationDate(creationDate)
                .gender(gender)
                .build();
    }

}
