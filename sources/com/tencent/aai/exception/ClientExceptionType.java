package com.tencent.aai.exception;

/* JADX INFO: loaded from: classes4.dex */
public enum ClientExceptionType {
    UNKNOWN_ERROR(-1, "Unknown exception"),
    AUDIO_RECORD_INIT_FAILED(-100, "AudioRecord init failed"),
    AUDIO_RECORD_START_FAILED(-101, "AudioRecord start failed"),
    AUDIO_RECORD_MULTIPLE_START(-102, "AudioRecord multi start"),
    AUDIO_RECOGNIZE_THREAD_START_FAILED(-103, "Audio recognize thread start failed"),
    AUDIO_SOURCE_DATA_NULL(-104, "Audio source data is null"),
    AUDIO_RECOGNIZE_REQUEST_NULL(-105, "AudioRecognizeRequest is null"),
    WEBSOCKET_NETWORK_FAILED(-106, "Websocket network exception");

    public final int code;
    public final String message;

    ClientExceptionType(int i, String str) {
        this.code = i;
        this.message = str;
    }
}
