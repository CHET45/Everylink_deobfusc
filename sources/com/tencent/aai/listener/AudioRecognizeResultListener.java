package com.tencent.aai.listener;

import com.tencent.aai.exception.ClientException;
import com.tencent.aai.exception.ServerException;
import com.tencent.aai.model.AudioRecognizeRequest;
import com.tencent.aai.model.AudioRecognizeResult;

/* JADX INFO: loaded from: classes4.dex */
public interface AudioRecognizeResultListener {
    void onFailure(AudioRecognizeRequest audioRecognizeRequest, ClientException clientException, ServerException serverException, String str);

    void onSegmentSuccess(AudioRecognizeRequest audioRecognizeRequest, AudioRecognizeResult audioRecognizeResult, int i);

    void onSliceSuccess(AudioRecognizeRequest audioRecognizeRequest, AudioRecognizeResult audioRecognizeResult, int i);

    void onSuccess(AudioRecognizeRequest audioRecognizeRequest, String str);
}
