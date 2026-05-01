package com.tencent.cloud.stream.tts;

/* JADX INFO: loaded from: classes4.dex */
public class TtsConstant {
    public static final String DEFAULT_HOST = "tts.cloud.tencent.com";
    public static final int DEFAULT_START_TIMEOUT_MILLISECONDS = 60000;
    public static final int DEFAULT_TTS_FLOWING_STOP_TIMEOUT_MILLISECONDS = 500;
    public static final String DEFAULT_TTS_V2_REQ_URL = "wss://tts.cloud.tencent.com/stream_wsv2";
    public static final String DEFAULT_TTS_V2_SIGN_PREFIX = "GETtts.cloud.tencent.com/stream_wsv2";
    private static final String FlowingSpeechSynthesizer_ACTION_COMPLETE = "ACTION_COMPLETE";
    private static final String FlowingSpeechSynthesizer_ACTION_SYNTHESIS = "ACTION_SYNTHESIS";

    public static String getFlowingSpeechSynthesizer_ACTION_SYNTHESIS() {
        return FlowingSpeechSynthesizer_ACTION_SYNTHESIS;
    }

    public static String getFlowingSpeechSynthesizer_ACTION_COMPLETE() {
        return FlowingSpeechSynthesizer_ACTION_COMPLETE;
    }
}
