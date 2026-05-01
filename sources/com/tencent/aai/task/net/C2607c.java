package com.tencent.aai.task.net;

import android.text.TextUtils;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.tencent.aai.auth.AbsCredentialProvider;
import com.tencent.aai.model.AudioRecognizeRequest;
import com.tencent.aai.task.config.C2603b;
import com.tencent.aai.task.net.networktime.C2610c;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: renamed from: com.tencent.aai.task.net.c */
/* JADX INFO: loaded from: classes4.dex */
public class C2607c {
    /* JADX INFO: renamed from: a */
    public static String m980a(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!z) {
                sb.append(PunctuationConst.AND);
            }
            sb.append(key).append(PunctuationConst.EQUAL).append(value);
            z = false;
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: a */
    public static String m981a(Map<String, String> map, C2603b c2603b, AbsCredentialProvider absCredentialProvider) throws UnsupportedEncodingException {
        String strM980a = m980a(map);
        if (TextUtils.isEmpty(strM980a)) {
            return "wss://asr.cloud.tencent.com/asr/v2/";
        }
        return "wss://asr.cloud.tencent.com/asr/v2/" + c2603b.m965a() + PunctuationConst.QUESTION_MARK + strM980a + "&signature=" + URLEncoder.encode(absCredentialProvider.getAudioRecognizeSign(String.format(Locale.CHINESE, "%s%s%s?%s", "asr.cloud.tencent.com", "/asr/v2/", Integer.valueOf(c2603b.m965a()), strM980a)), "UTF-8");
    }

    /* JADX INFO: renamed from: a */
    public static Map<String, String> m982a(String str, AudioRecognizeRequest audioRecognizeRequest, C2603b c2603b) {
        C2610c.m986c().m990d();
        audioRecognizeRequest.UpdateTimestamp();
        audioRecognizeRequest.getEngineModelType();
        TreeMap treeMap = new TreeMap();
        for (String str2 : audioRecognizeRequest.getApiParams().keySet()) {
            treeMap.put(str2, String.valueOf(audioRecognizeRequest.getApiParam(str2)));
        }
        treeMap.put("secretid", c2603b.m966b());
        treeMap.put("voice_id", str);
        treeMap.put("timestamp", String.valueOf(audioRecognizeRequest.getTimestamp() + C2610c.m986c().m987a()));
        treeMap.put("expired", String.valueOf(audioRecognizeRequest.getTimestamp() + 36000 + C2610c.m986c().m987a()));
        treeMap.put("nonce", String.valueOf(audioRecognizeRequest.getTimestamp() + C2610c.m986c().m987a()));
        treeMap.put("voice_format", String.valueOf(audioRecognizeRequest.getVoice_format()));
        return treeMap;
    }
}
