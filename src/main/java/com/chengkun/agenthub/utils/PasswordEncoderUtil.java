package com.chengkun.agenthub.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        String password = "123456";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String passwordEncoded = encoder.encode(password);
        System.out.println(passwordEncoded);

        final boolean matches = encoder.matches(password, passwordEncoded);
        System.out.println(matches);
    }

}
