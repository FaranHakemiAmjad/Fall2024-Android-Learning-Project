package org.bihe.faranha.lowercaseimdb.utils;

public class FormValidator {
    public static boolean hasNullOrEmpty(String... inputs) {
        for (String input : inputs) {
            if (input == null || input.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
