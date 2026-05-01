package com.tencent.qcloud.network.sonar.http;

import com.azure.core.implementation.logging.LoggingKeys;
import com.tencent.qcloud.network.sonar.utils.SonarLog;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class HttpResult {
    public boolean bypassProxy;
    public String domain;
    public int responseCode;
    public Map<String, List<String>> responseHeaders;
    public long timeConsuming;
    public String url;

    public String encode() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(LoggingKeys.HTTP_METHOD_KEY, "http");
            jSONObject.put("domain", this.domain);
            jSONObject.put("url", this.url);
            jSONObject.put("bypassProxy", this.bypassProxy);
            jSONObject.put("responseCode", this.responseCode);
            Map<String, List<String>> map = this.responseHeaders;
            if (map != null) {
                jSONObject.put("responseHeaders", map);
            }
            jSONObject.put("timeConsuming", this.timeConsuming);
            return jSONObject.toString();
        } catch (JSONException e) {
            if (!SonarLog.openLog) {
                return null;
            }
            e.printStackTrace();
            return null;
        }
    }

    public String toString() {
        return encode();
    }
}
