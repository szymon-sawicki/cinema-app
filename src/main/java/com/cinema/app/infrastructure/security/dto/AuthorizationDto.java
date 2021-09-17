package com.cinema.app.infrastructure.security.dto;

import com.cinema.app.domain.user.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AuthorizationDto {

    private Long id;
    private String username;
    private Role role;
}
