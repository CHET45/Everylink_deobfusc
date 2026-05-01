package com.tencent.aai.task.listener;

import com.tencent.aai.task.AudioPcmData;

/* JADX INFO: loaded from: classes4.dex */
public interface AudioRecognizeBufferListener {
    boolean onSliceComplete(AudioPcmData audioPcmData);
}
