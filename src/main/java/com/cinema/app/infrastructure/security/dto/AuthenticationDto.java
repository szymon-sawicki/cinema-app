package com.cinema.app.infrastructure.security.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationDto {

    private String username;
    private String password;

}
