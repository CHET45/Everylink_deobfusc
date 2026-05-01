package com.aivox.besota.sdk.utils;

/* JADX INFO: loaded from: classes.dex */
public enum AudioChannel {
    UNKNOWN(-1, "Unknown"),
    NONE_CHANNEL(0, "NONE_CHANNEL"),
    STEREO_LEFT(1, "LEFT_CHANNEL"),
    STEREO_RIGHT(2, "RIGHT_CHANNEL");

    private String mName;
    private int mValue;

    AudioChannel(int i, String str) {
        this.mValue = i;
        this.mName = str;
    }

    public static AudioChannel getChannel(int i) {
        if (i == 0) {
            return NONE_CHANNEL;
        }
        if (i == 1) {
            return STEREO_LEFT;
        }
        if (i == 2) {
            return STEREO_RIGHT;
        }
        return UNKNOWN;
    }

    public int getValue() {
        return this.mValue;
    }

    public String getName() {
        return this.mName;
    }

    @Override // java.lang.Enum
    public String toString() {
        return "AudioChannel{mValue=" + this.mValue + ", mName='" + this.mName + "'}";
    }
}
