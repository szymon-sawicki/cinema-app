package com.cinema.app.infrastructure.security;


import com.cinema.app.domain.user.UserUtils;
import com.cinema.app.infrastructure.persistence.dao.UserEntityDao;
import com.cinema.app.infrastructure.persistence.entity.UserEntity;
import com.cinema.app.infrastructure.security.dto.AuthenticationDto;
import com.cinema.app.infrastructure.security.dto.AuthorizationDto;
import com.cinema.app.infrastructure.security.dto.RefreshTokenDto;
import com.cinema.app.infrastructure.security.dto.TokensDto;
import com.cinema.app.infrastructure.security.exception.AppTokensServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.cinema.app.application.service.configs.AppPasswordEncoder.check;


@Service
@RequiredArgsConstructor
public class AppTokensService {

    @Value("${tokens.access.token.expiration.time.ms}")
    private Long accessTokenExpirationTimeMs;

    @Value("${tokens.refresh.token.expiration.time.ms}")
    private Long refreshTokenExpirationTimeMs;

    @Value("${tokens.access.token.expiration.time.ms.property}")
    private String refreshTokenProperty;

    @Value("${tokens.prefix}")
    private String tokensPrefix;

    private final UserEntityDao userEntityDao;
    private final SecretKey secretKey;


    public TokensDto generateTokens(AuthenticationDto authenticationDto) {
        var username = authenticationDto.getUsername();
        var user = userEntityDao
                .findByUsername(username)
                .map(UserEntity::toUser)
                .orElseThrow(() -> new AppTokensServiceException("Cannot find user " + username));

        var userId = UserUtils.toId.apply(user);
        var password = UserUtils.toPassword.apply(user);

        if(!check(authenticationDto.getPassword(),password)) {
            throw new AppTokensServiceException("password is not correct");
        }


        var currentDate = new Date();
        var accessTokenExpirationDate = new Date(currentDate.getTime() + accessTokenExpirationTimeMs);
        var refreshTokenExpirationDate = new Date(currentDate.getTime() + refreshTokenExpirationTimeMs);

        var accessToken = Jwts
                .builder()
                .setSubject(String.valueOf(userId))
                .setExpiration(accessTokenExpirationDate)
                .setIssuedAt(currentDate)
                .signWith(secretKey)
                .compact();

        var refreshToken = Jwts
                .builder()
                .setSubject(String.valueOf(userId))
                .setExpiration(refreshTokenExpirationDate)
                .setIssuedAt(currentDate)
                .claim(refreshTokenProperty, accessTokenExpirationDate.getTime())
                .signWith(secretKey)
                .compact();

        return TokensDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    // -------------------------------------------------------------------------------------------------------------
    // PARSING TOKENS
    // -------------------------------------------------------------------------------------------------------------

    public AuthorizationDto parseAccessToken(String header) {
        if (header == null) {
            throw new AppTokensServiceException("Header is null");
        }

        // TODO fix

/*
        if (!header.startsWith(tokensPrefix)) {
            throw new AppTokensServiceException("Token has invalid format");
        }
*/

       var token = header.replaceAll(tokensPrefix, "");
        var userId = id(token);

        return userEntityDao
                .findById(userId)
                .map(user ->  user.toUser().toAuthorizationDto())
                .orElseThrow(() -> new AppTokensServiceException("Authorization failed!"));

    }

    // -------------------------------------------------------------------------------------------------------------
    // GENEROWANIE NOWYCH TOKENOW NA PODSTAWIE REFRESH TOKEN
    // -------------------------------------------------------------------------------------------------------------
    public TokensDto refreshTokens(RefreshTokenDto refreshTokenDto) {
        if (refreshTokenDto == null) {
            throw new AppTokensServiceException("Refresh token object is null");
        }

        var refreshToken = refreshTokenDto.getToken();
        if (refreshToken == null) {
            throw new AppTokensServiceException("Token is null");
        }

        if (isTokenNotValid(refreshToken)) {
            throw new AppTokensServiceException("Refresh token has been expired");
        }

        var accessTokenExpirationTimeMsFromToken = accessTokenExpirationTimeMs(refreshToken);
        if (accessTokenExpirationTimeMsFromToken < System.currentTimeMillis()) {
            throw new AppTokensServiceException("Cannot refresh tokens because of access token expiration");
        }

        var userId = id(refreshToken);
        var currentDate = new Date();
        var accessTokenExpirationDate = new Date(currentDate.getTime() + accessTokenExpirationTimeMs);
        var refreshTokenExpirationDate = expirationDate(refreshToken);

        var accessToken = Jwts
                .builder()
                .setSubject(String.valueOf(userId))
                .setExpiration(accessTokenExpirationDate)
                .setIssuedAt(currentDate)
                .signWith(secretKey)
                .compact();

        var newRefreshToken = Jwts
                .builder()
                .setSubject(String.valueOf(userId))
                .setExpiration(refreshTokenExpirationDate)
                .setIssuedAt(currentDate)
                .claim(refreshTokenProperty, accessTokenExpirationDate.getTime())
                .signWith(secretKey)
                .compact();

        return TokensDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    private Claims claims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Long id(String token) {
        return Long.parseLong(claims(token).getSubject());
    }

    private Date expirationDate(String token) {
        return claims(token).getExpiration();
    }

    private boolean isTokenNotValid(String token) {
        return expirationDate(token).before(new Date());
    }

    private long accessTokenExpirationTimeMs(String token) {
        return claims(token).get(refreshTokenProperty, Long.class);
    }
}
