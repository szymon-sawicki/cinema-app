package com.cinema.app.application.service.configs;



import com.cinema.app.application.service.exception.AppPasswordEncoderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static com.cinema.app.application.service.configs.AppPasswordEncoder.*;

public class AppPasswordEncoderTest {
    @Test
    @DisplayName("when password to encode is null")
    public void test1() {
        assertThatThrownBy(() -> encode(null))
                .isInstanceOf(AppPasswordEncoderException.class)
                .hasMessage("Password is null");
    }

    @Test
    @DisplayName("when password to encode is not null")
    public void test2() {
        var password = "1234";
        assertThat(encode(password))
                .isNotNull()
                .isNotBlank();
    }

    @Test
    @DisplayName("when given password is null")
    public void test3() {
        assertThatThrownBy(() -> check(null, ""))
                .isInstanceOf(AppPasswordEncoderException.class)
                .hasMessage("Password is null");
    }

    @Test
    @DisplayName("when encoded password is null")
    public void test4() {
        assertThatThrownBy(() -> check("", null))
                .isInstanceOf(AppPasswordEncoderException.class)
                .hasMessage("Encoded password is null");
    }

    @Test
    @DisplayName("when encoded password matches given password")
    public void test5() {
        var password = "1234";
        var encodedPassword = encode(password);
        assertThat(check(password, encodedPassword)).isTrue();
    }

    @Test
    @DisplayName("when encoded password does not match given password")
    public void test6() {
        var password = "1234";
        var encodedPassword = encode(password);
        assertThat(check(password + "5", encodedPassword)).isFalse();
    }
}
