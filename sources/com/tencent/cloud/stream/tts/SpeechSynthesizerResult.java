package com.tencent.cloud.stream.tts;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechSynthesizerResult {

    @SerializedName("subtitles")
    private SpeechSynthesizerSubtitle[] subtitles;

    public SpeechSynthesizerSubtitle[] getSubtitles() {
        return this.subtitles;
    }

    public void setSubtitles(SpeechSynthesizerSubtitle[] subtitles) {
        this.subtitles = subtitles;
    }
}
