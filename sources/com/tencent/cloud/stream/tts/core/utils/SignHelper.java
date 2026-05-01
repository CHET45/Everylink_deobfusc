package com.tencent.cloud.stream.tts.core.utils;

import com.github.houbb.heaven.constant.PunctuationConst;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import kotlin.text.Typography;

/* JADX INFO: loaded from: classes4.dex */
public class SignHelper {
    public static String createUrl(Map<String, Object> paramMap) {
        StringBuilder sb = new StringBuilder();
        sb.append(PunctuationConst.QUESTION_MARK);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (entry.getValue() != null && entry.getValue() != "") {
                sb.append(entry.getKey());
                sb.append('=');
                sb.append(entry.getValue());
                sb.append(Typography.amp);
            }
        }
        if (paramMap.size() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    public static Map<String, Object> encode(Map<String, Object> src) throws UnsupportedEncodingException {
        if (src != null) {
            for (String str : src.keySet()) {
                if (src.get(str) instanceof String) {
                    src.put(str, URLEncoder.encode(String.valueOf(src.get(str)), "UTF-8"));
                }
            }
        }
        return src;
    }
}
