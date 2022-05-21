package com.quickretry.spring;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author tian
 * @date 2021年05月31日 15:24
 */
public class RandomUtil {
    public static String randomNumbers(int length) {
        return randomString("0123456789", length);
    }

    public static String randomString(String baseString, int length) {
        StringBuilder sb = new StringBuilder();
        if (length < 1) {
            length = 1;
        }

        int baseLength = baseString.length();

        for (int i = 0; i < length; ++i) {
            int number = ThreadLocalRandom.current().nextInt(baseLength);
            sb.append(baseString.charAt(number));
        }

        return sb.toString();
    }
}
