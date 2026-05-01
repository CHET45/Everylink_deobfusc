package com.aivox.common.util.tts;

/* JADX INFO: loaded from: classes.dex */
public interface TtsManager {

    public interface TtsListener {
        void onTtsError(String str);

        void onTtsStart();

        void onTtsStop();
    }

    void init(String str);

    void release();

    void setTtsListener(TtsListener ttsListener);

    void speak(String str, boolean z);

    void stop();
}
