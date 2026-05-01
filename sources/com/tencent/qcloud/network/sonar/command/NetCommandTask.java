package com.tencent.qcloud.network.sonar.command;

import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes4.dex */
public abstract class NetCommandTask<T> extends CommandTask<T> {
    protected static final String MATCH_PING_IP = "(?<=from ).*(?=: icmp_seq=1 ttl=)";
    protected static final String MATCH_PING_TIME = "(?<=time=).*?ms";
    protected static final String MATCH_PING_TTL = "(?<=ttl=).*(?= time)";
    protected static final String MATCH_TRACE_IP = "(?<=From )(?:[0-9]{1,3}\\.){3}[0-9]{1,3}";
    protected static final String MATCH_TTL_EXCEEDED = "Time to live exceeded";

    protected Matcher matcherRouteNode(String str) {
        return Pattern.compile(MATCH_TRACE_IP).matcher(str);
    }

    protected Matcher matcherTime(String str) {
        return Pattern.compile(MATCH_PING_TIME).matcher(str);
    }

    protected Matcher matcherTTL(String str) {
        return Pattern.compile(MATCH_PING_TTL).matcher(str);
    }

    protected Matcher matcherIp(String str) {
        return Pattern.compile(MATCH_PING_IP).matcher(str);
    }

    protected Matcher matcherTTLExceeded(String str) {
        return Pattern.compile(MATCH_TTL_EXCEEDED).matcher(str);
    }

    protected String getIpFromMatcher(Matcher matcher) {
        String strGroup = matcher.group();
        int iIndexOf = strGroup.indexOf(40);
        return iIndexOf >= 0 ? strGroup.substring(iIndexOf + 1) : strGroup;
    }

    protected String getPingDelayFromMatcher(Matcher matcher) {
        String strGroup;
        if (!matcher.find()) {
            strGroup = "0";
        } else {
            strGroup = matcher.group();
            if (!TextUtils.isEmpty(strGroup)) {
                strGroup = strGroup.replace(" ms", "");
            }
        }
        return strGroup.trim();
    }

    protected String getPingTTLFromMatcher(Matcher matcher) {
        String strGroup;
        if (!matcher.find()) {
            strGroup = "0";
        } else {
            strGroup = matcher.group();
        }
        return strGroup.trim();
    }
}
