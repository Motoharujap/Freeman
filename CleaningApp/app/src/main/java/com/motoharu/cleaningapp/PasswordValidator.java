package com.motoharu.cleaningapp;

import java.net.PasswordAuthentication;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Motoharu on 08.10.2014.
 */
public class PasswordValidator {
    private Pattern pattern;
    private Matcher matcher;
    public static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9+._%-+]{1,100}" + "@"
            + "[a-zA-Z0-9][a-zA-Z0-9-]{1,10}" + "(" + "."
            + "[a-zA-Z0-9][a-zA-Z0-9-]{1,20}"+
            ")+");

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9+_.]{6,16}");

    public boolean validatePassword(String password)
    {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public boolean validateEmail(String email)
    {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
