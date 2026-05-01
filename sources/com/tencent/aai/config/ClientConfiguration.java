package com.tencent.aai.config;

/* JADX INFO: loaded from: classes4.dex */
public class ClientConfiguration {
    private static int AudioRecognizeWriteTimeout = 5000;
    private static int audioRecognizeConnectTimeout = 3000;
    private static int audioRecognizeSliceTimeout = 5000;

    private ClientConfiguration() {
    }

    public static int getAudioRecognizeConnectTimeout() {
        return audioRecognizeConnectTimeout;
    }

    public static int getAudioRecognizeReadTimeout() {
        return audioRecognizeSliceTimeout;
    }

    public static int getAudioRecognizeSliceTimeout() {
        return audioRecognizeSliceTimeout;
    }

    public static int getAudioRecognizeWriteTimeout() {
        return AudioRecognizeWriteTimeout;
    }

    public static void setAudioRecognizeConnectTimeout(int i) {
        if (i < 500 || i > 10000) {
            return;
        }
        audioRecognizeConnectTimeout = i;
    }

    public static void setAudioRecognizeSliceTimeout(int i) {
        if (i < 500 || i > 10000) {
            return;
        }
        audioRecognizeSliceTimeout = i;
    }

    public static void setAudioRecognizeWriteTimeout(int i) {
        if (i < 500 || i > 10000) {
            return;
        }
        AudioRecognizeWriteTimeout = i;
    }
}
