package com.aivox.besota.sdk.utils;

/* JADX INFO: loaded from: classes.dex */
public enum OTAChannel {
    CHANNEL_UNKNOWN(-1, "OTA Channel Unknown"),
    CHANNEL_APP(0, "OTA Channel App"),
    CHANNEL_GOOGLE_AI(1, "OTA Channel Google Assistant"),
    CHANNEL_AMAZON_AI(2, "OTA Channel Amazon Alexa"),
    STATUS_XIAOWEI_AI(3, "OTA Channel Xiaowei");

    private String mName;
    private int mValue;

    OTAChannel(int i, String str) {
        this.mValue = i;
        this.mName = str;
    }

    public int getValue() {
        return this.mValue;
    }

    public String getName() {
        return this.mName;
    }
}
