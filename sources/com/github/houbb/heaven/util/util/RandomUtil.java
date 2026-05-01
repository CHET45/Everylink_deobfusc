package com.github.houbb.heaven.util.util;

import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/* JADX INFO: loaded from: classes3.dex */
public final class RandomUtil {
    private static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";
    private static final String BASE_CHAR_NUMBER = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final String BASE_NUMBER = "0123456789";

    private RandomUtil() {
    }

    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }

    public static SecureRandom getSecureRandom() {
        try {
            return SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static Random getRandom(boolean z) {
        return z ? getSecureRandom() : getRandom();
    }

    public static String randomChar(int i) {
        return randomString(BASE_CHAR, i);
    }

    public static String randomCharNumber(int i) {
        return randomString(BASE_CHAR_NUMBER, i);
    }

    public static String randomNumber(int i) {
        return randomString(BASE_NUMBER, i);
    }

    public static String randomString(String str, int i) {
        StringBuilder sb = new StringBuilder();
        if (i < 1) {
            i = 1;
        }
        int length = str.length();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(str.charAt(ThreadLocalRandom.current().nextInt(length)));
        }
        return sb.toString();
    }

    public static <T> T random(List<T> list) {
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    public static int random(int i, int i2) {
        return i + ThreadLocalRandom.current().nextInt(i2);
    }

    public static int random(int i) {
        return random(0, i);
    }
}
