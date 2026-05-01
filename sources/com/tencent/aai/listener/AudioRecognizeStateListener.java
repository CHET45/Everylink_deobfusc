package com.tencent.aai.listener;

import com.tencent.aai.model.AudioRecognizeRequest;

/* JADX INFO: loaded from: classes4.dex */
public interface AudioRecognizeStateListener {
    void onNextAudioData(short[] sArr, int i);

    void onSilentDetectTimeOut();

    void onStartRecord(AudioRecognizeRequest audioRecognizeRequest);

    void onStopRecord(AudioRecognizeRequest audioRecognizeRequest);

    void onVoiceDb(float f);

    void onVoiceVolume(AudioRecognizeRequest audioRecognizeRequest, int i);
}
