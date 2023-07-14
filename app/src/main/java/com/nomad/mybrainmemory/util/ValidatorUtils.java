package com.nomad.mybrainmemory.util;

import java.util.regex.Pattern;

public class ValidatorUtils {
    /*
    (?=.*[0-9]) requires at least one digit.
    (?=.*[a-zA-Z]) requires at least one letter.
    (?=.*[@#$%^&+=]) requires at least one special character (you can modify this set as needed).
    (?=\\S+$) ensures that there are no whitespace characters.
    .{6,8} specifies the allowed length range (6 to 8 characters).
     */
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,8}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isPasswordValid(String password) {
        return pattern.matcher(password).matches();
    }


    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isEmailValid(String email) {
        return emailPattern.matcher(email).matches();
    }


}
