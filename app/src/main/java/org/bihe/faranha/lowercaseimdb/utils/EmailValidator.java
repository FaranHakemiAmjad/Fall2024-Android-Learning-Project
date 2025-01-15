package org.bihe.faranha.lowercaseimdb.utils;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class EmailValidator {

    public static final String EMAIL_REGEX = "^([^@])+@+(.*)\\.com";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
