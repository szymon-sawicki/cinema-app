package com.cinema.app.domain.user;

import com.cinema.app.domain.user.dto.CreateUserResponseDto;
import com.cinema.app.domain.user.dto.GetUserDto;
import com.cinema.app.domain.user.type.Gender;
import com.cinema.app.domain.user.type.Role;
import com.cinema.app.infrastructure.persistence.entity.UserEntity;
import com.cinema.app.infrastructure.security.dto.AuthorizationDto;
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
    Role role;
    LocalDate birthDate;
    LocalDate creationDate;
    Gender gender;

    public GetUserDto toGetUserDto() {
        return GetUserDto.builder()
                .id(id)
                .username(username)
                .mail(mail)
                .name(name)
                .role(role)
                .birthDate(birthDate)
                .creationDate(creationDate)
                .gender(gender)
                .build();
    }

    public CreateUserResponseDto toCreateUserResponseDto() {
        return CreateUserResponseDto.builder()
                .id(id)
                .username(username)
                .build();
    }

    public User withCreationDate(LocalDate creationDate) {
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .mail(mail)
                .name(name)
                .role(role)
                .birthDate(birthDate)
                .creationDate(creationDate)
                .gender(gender)
                .build();
    }

    public User withPassword(String newPassword) {
        return User.builder()
                .id(id)
                .username(username)
                .password(newPassword)
                .mail(mail)
                .name(name)
                .role(role)
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
                .role(role)
                .password(password)
                .birthDate(birthDate)
                .creationDate(creationDate)
                .gender(gender)
                .build();
    }

    public AuthorizationDto toAuthorizationDto() {
        return AuthorizationDto.builder()
                .id(id)
                .username(username)
                .role(role)
                .build();
    }

}
