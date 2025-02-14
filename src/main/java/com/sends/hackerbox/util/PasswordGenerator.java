package com.sends.hackerbox.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%&*.";
    private static final int PASSWORD_LENGTH = 12;

    public static String generateStrongPassword() {
        SecureRandom random = new SecureRandom();
        List<Character> passwordChars = new ArrayList<>();

        // 至少包含一个大写字母
        passwordChars.add(UPPER.charAt(random.nextInt(UPPER.length())));
        // 至少包含一个小写字母
        passwordChars.add(LOWER.charAt(random.nextInt(LOWER.length())));
        // 至少包含一个数字
        passwordChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        // 至少包含一个特殊字符
        passwordChars.add(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        // 填充剩余的字符
        String allChars = UPPER + LOWER + DIGITS + SPECIAL;
        for (int i = passwordChars.size(); i < PASSWORD_LENGTH; i++) {
            passwordChars.add(allChars.charAt(random.nextInt(allChars.length())));
        }

        // 打乱字符顺序
        Collections.shuffle(passwordChars, random);

        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }
}
