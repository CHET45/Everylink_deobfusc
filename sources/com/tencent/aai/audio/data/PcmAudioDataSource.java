package com.tencent.aai.audio.data;

import com.tencent.aai.exception.ClientException;

/* JADX INFO: loaded from: classes4.dex */
public interface PcmAudioDataSource {
    boolean isSetSaveAudioRecordFiles();

    int read(short[] sArr, int i);

    void start() throws ClientException;

    void stop();
}
