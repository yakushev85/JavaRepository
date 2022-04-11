package org.oiakushev.hwjbackendwizard.util;

import java.util.Random;

public class PasswordUtil {
    private static final String DEFAULT_ALPHABET = "qwertyuiopasdfghjklzxcvbnm_QWERTYUIOPASDFGHJKLZXCVBNM-1234567890";
    private static final int DEFAULT_LENGTH = 12;
    private static final Random random = new Random();

    public static String generatePassword(int length) {
        StringBuilder result = new StringBuilder();
        int lenAlp = DEFAULT_ALPHABET.length();

        for (int i=0;i<length;i++) {
            result.append(DEFAULT_ALPHABET.charAt(random.nextInt(lenAlp)));
        }

        return result.toString();
    }

    public static String generatePassword() {
        return generatePassword(DEFAULT_LENGTH);
    }
}
