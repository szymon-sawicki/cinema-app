package com.cinema.app.application.service.configs;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.cinema.app.application.service.exception.AppPasswordEncoderException;

public interface AppPasswordEncoder {

    static String encode(String password) {
        if (password == null) {
            throw new AppPasswordEncoderException("Password is null");
        }
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    static boolean check(String password, String encodedPassword) {
        if (password == null) {
            throw new AppPasswordEncoderException("Password is null");
        }
        if (encodedPassword == null) {
            throw new AppPasswordEncoderException("Encoded password is null");
        }
        return BCrypt.verifyer().verify(password.toCharArray(), encodedPassword).verified;
    }
}
