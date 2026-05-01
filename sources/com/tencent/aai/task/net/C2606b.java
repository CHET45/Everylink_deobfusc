package com.tencent.aai.task.net;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

/* JADX INFO: renamed from: com.tencent.aai.task.net.b */
/* JADX INFO: loaded from: classes4.dex */
public class C2606b {

    /* JADX INFO: renamed from: a */
    public static String f1004a = String.valueOf(UUID.randomUUID().hashCode());

    /* JADX INFO: renamed from: a */
    public static String m977a() {
        return m979a(f1004a + ("" + (System.currentTimeMillis() * 1000000)) + m978a(8) + System.currentTimeMillis(), false);
    }

    /* JADX INFO: renamed from: a */
    public static String m978a(int i) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append("zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890".charAt(random.nextInt(62)));
        }
        return stringBuffer.toString();
    }

    /* JADX INFO: renamed from: a */
    public static String m979a(String str, boolean z) {
        StringBuffer stringBuffer = new StringBuffer("");
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            byte[] bArrDigest = messageDigest.digest();
            for (int i = 0; i < bArrDigest.length; i++) {
                int i2 = bArrDigest[i];
                if (i2 < 0) {
                    i2 += 256;
                }
                if (i2 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i2));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String string = stringBuffer.toString();
        return z ? string : string.substring(8, 24);
    }
}
