package com.example.mocknetwork.util;

import java.util.regex.Pattern;

public class ValidationUtil {
    /**
     * Validates a username and password
     * Must contains one number and one character
     *
     * @param username The frequent username to validate.
     * @return True, if the frequent username is valid, false otherwise.
     */
    public static boolean isValidUsernameAndPassword(String username) {
        String pattern = "([A-Za-z]+[0-9]|[0-9]+[A-Za-z])[A-Za-z0-9]*";
        return Pattern.matches(pattern, username);
    }
}
