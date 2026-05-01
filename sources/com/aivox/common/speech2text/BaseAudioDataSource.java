package com.aivox.common.speech2text;

import com.tencent.aai.audio.data.PcmAudioDataSource;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseAudioDataSource implements PcmAudioDataSource {
    public abstract void changePause(boolean z, boolean z2);

    public abstract byte[] getData();

    public abstract boolean isEmpty();

    public abstract boolean isRecording();

    public abstract void setRecognizing(boolean z);

    public abstract void startRecord(boolean z);

    public abstract void stopRecord();
}
