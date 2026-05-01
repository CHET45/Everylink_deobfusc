package com.github.houbb.heaven.util.net;

import com.clj.fastble.data.BleMsg;
import com.github.houbb.heaven.util.lang.StringUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/* JADX INFO: loaded from: classes3.dex */
public class EncodeUtil {
    public static String encode(String str) {
        return encode(str, "UTF-8");
    }

    public static String encode(String str, String str2) {
        try {
            return URLEncoder.encode(str, str2);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encodeUnicode(String str) {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(str.length() * 6);
        stringBuffer.setLength(0);
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            stringBuffer.append("\\u");
            String hexString = Integer.toHexString(cCharAt >>> '\b');
            if (hexString.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(hexString);
            String hexString2 = Integer.toHexString(cCharAt & 255);
            if (hexString2.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(hexString2);
        }
        return new String(stringBuffer);
    }

    public static String decodeUnicode(String str) {
        int i;
        if (StringUtil.isEmpty(str) || !str.contains("\\u")) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length() / 6);
        int i2 = 0;
        while (i2 <= str.length() - 6) {
            int i3 = i2 + 6;
            String strSubstring = str.substring(i2, i3).substring(2);
            int iPow = 0;
            for (int i4 = 0; i4 < strSubstring.length(); i4++) {
                char cCharAt = strSubstring.charAt(i4);
                switch (cCharAt) {
                    case BleMsg.MSG_SET_MTU_START /* 97 */:
                        i = 10;
                        break;
                    case BleMsg.MSG_SET_MTU_RESULT /* 98 */:
                        i = 11;
                        break;
                    case 'c':
                        i = 12;
                        break;
                    case 'd':
                        i = 13;
                        break;
                    case 'e':
                        i = 14;
                        break;
                    case 'f':
                        i = 15;
                        break;
                    default:
                        i = cCharAt - '0';
                        break;
                }
                iPow += i * ((int) Math.pow(16.0d, (strSubstring.length() - i4) - 1));
            }
            sb.append((char) iPow);
            i2 = i3;
        }
        return sb.toString();
    }
}
