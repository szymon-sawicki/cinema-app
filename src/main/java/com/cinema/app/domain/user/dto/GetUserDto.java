package com.cinema.app.domain.user.dto;

import com.cinema.app.domain.user.type.Gender;
import com.cinema.app.domain.user.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class GetUserDto {

    Long id;
    String username;
    String mail;
    String name;
    LocalDate birthDate;
    LocalDate creationDate;
    Role role;
    Gender gender;

}
