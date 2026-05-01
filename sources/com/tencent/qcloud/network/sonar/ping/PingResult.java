package com.tencent.qcloud.network.sonar.ping;

import com.azure.core.implementation.logging.LoggingKeys;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.tencent.qcloud.network.sonar.utils.SonarLog;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PingResult {
    public float avg;
    public int count;
    public int dropped;
    public final String host;
    public final int interval;

    /* JADX INFO: renamed from: ip */
    public final String f1876ip;
    public float max;
    public float min;
    public final String result;
    public int sent;
    public final int size;
    public float stddev;
    public long timeConsuming;
    private final String lastLinePrefix = "rtt min/avg/max/mdev = ";
    private final String packetWords = " packets transmitted";
    private final String receivedWords = " received";

    PingResult(String str, String str2, String str3, int i, int i2) {
        this.result = str;
        this.f1876ip = str3;
        this.size = i;
        this.interval = i2;
        this.host = str2;
        parseResult();
    }

    static String trimNoneDigital(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        char[] charArray = str.toCharArray();
        char[] cArr = new char[charArray.length];
        int i = 0;
        for (char c : charArray) {
            if ((c >= '0' && c <= '9') || c == '.') {
                cArr[i] = c;
                i++;
            }
        }
        return new String(cArr, 0, i);
    }

    private void parseRttLine(String str) {
        String[] strArrSplit = str.substring("rtt min/avg/max/mdev = ".length(), str.length() - 3).split("/");
        if (strArrSplit.length != 4) {
            return;
        }
        this.min = Float.parseFloat(trimNoneDigital(strArrSplit[0]));
        this.avg = Float.parseFloat(trimNoneDigital(strArrSplit[1]));
        this.max = Float.parseFloat(trimNoneDigital(strArrSplit[2]));
        this.stddev = Float.parseFloat(trimNoneDigital(strArrSplit[3]));
    }

    private void parsePacketLine(String str) {
        String[] strArrSplit = str.split(PunctuationConst.COMMA);
        if (strArrSplit.length != 4) {
            return;
        }
        if (strArrSplit[0].length() > " packets transmitted".length()) {
            String str2 = strArrSplit[0];
            this.count = Integer.parseInt(str2.substring(0, str2.length() - " packets transmitted".length()));
        }
        if (strArrSplit[1].length() > " received".length()) {
            String str3 = strArrSplit[1];
            this.sent = Integer.parseInt(str3.substring(0, str3.length() - " received".length()).trim());
        }
        this.dropped = this.count - this.sent;
    }

    private void parseResult() {
        try {
            for (String str : this.result.split("\n")) {
                if (str.contains(" packets transmitted")) {
                    parsePacketLine(str);
                } else if (str.contains("rtt min/avg/max/mdev = ")) {
                    parseRttLine(str);
                }
            }
        } catch (Exception e) {
            if (SonarLog.openLog) {
                e.printStackTrace();
            }
        }
    }

    public int getResponseNum() {
        return this.sent;
    }

    public float getLoss() {
        int i = this.count;
        if (i == 0) {
            return 1.0f;
        }
        return this.dropped / i;
    }

    public String encode() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(LoggingKeys.HTTP_METHOD_KEY, "ping");
            jSONObject.put("ip", this.f1876ip);
            jSONObject.put("host", this.host);
            jSONObject.put("max", String.format("%.2f", Float.valueOf(this.max)));
            jSONObject.put("min", String.format("%.2f", Float.valueOf(this.min)));
            jSONObject.put("avg", String.format("%.2f", Float.valueOf(this.avg)));
            jSONObject.put("stddev", String.format("%.2f", Float.valueOf(this.stddev)));
            if (this.count != 0) {
                jSONObject.put("loss", String.format("%.2f", Float.valueOf(Float.valueOf(this.dropped).floatValue() / Float.valueOf(this.count).floatValue())));
            } else {
                jSONObject.put("loss", "1");
            }
            jSONObject.put("count", this.count);
            jSONObject.put("size", this.size);
            jSONObject.put("responseNum", this.sent);
            jSONObject.put("interval", this.interval);
            jSONObject.put("timestamp", System.currentTimeMillis() / 1000);
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
