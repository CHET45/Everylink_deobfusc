package com.tencent.aai.task.listener;

import com.tencent.aai.exception.ClientException;

/* JADX INFO: loaded from: classes4.dex */
public interface AudioRecognizerListener {
    void audioDatas(short[] sArr, int i);

    void onError(ClientException clientException);

    void onFinish();

    void onStart();

    void onVoiceDb(float f);

    void onVolume(int i);
}
