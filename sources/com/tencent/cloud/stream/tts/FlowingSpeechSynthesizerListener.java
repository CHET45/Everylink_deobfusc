package com.tencent.cloud.stream.tts;

import com.google.gson.Gson;
import com.tencent.cloud.stream.tts.core.p032ws.ConnectionListener;
import com.tencent.cloud.stream.tts.core.utils.AAILogger;
import java.nio.ByteBuffer;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public abstract class FlowingSpeechSynthesizerListener implements ConnectionListener {
    public static final String TAG = "FlowingSpeechSynthesizerListener";
    private String status = "init";
    protected FlowingSpeechSynthesizer synthesizer;

    public abstract void onAudioResult(ByteBuffer data);

    public abstract void onSynthesisCancel();

    public abstract void onSynthesisEnd(SpeechSynthesizerResponse response);

    public abstract void onSynthesisFail(SpeechSynthesizerResponse response);

    public abstract void onSynthesisStart(SpeechSynthesizerResponse response);

    public abstract void onTextResult(SpeechSynthesizerResponse response);

    public void setSpeechSynthesizer(FlowingSpeechSynthesizer synthesizer) {
        this.synthesizer = synthesizer;
    }

    @Override // com.tencent.cloud.stream.tts.core.p032ws.ConnectionListener
    public void onOpen() {
        AAILogger.m1853d(TAG, "onOpen is ok");
    }

    @Override // com.tencent.cloud.stream.tts.core.p032ws.ConnectionListener
    public void onClose(int closeCode, String reason) {
        FlowingSpeechSynthesizer flowingSpeechSynthesizer = this.synthesizer;
        if (flowingSpeechSynthesizer != null) {
            flowingSpeechSynthesizer.markClosed();
        }
        AAILogger.m1853d(TAG, String.format("connection is closed due to %s, code:%d", reason, Integer.valueOf(closeCode)));
    }

    @Override // com.tencent.cloud.stream.tts.core.p032ws.ConnectionListener
    public void onCancel() {
        onSynthesisCancel();
        FlowingSpeechSynthesizer flowingSpeechSynthesizer = this.synthesizer;
        if (flowingSpeechSynthesizer != null) {
            flowingSpeechSynthesizer.markClosed();
        }
        AAILogger.m1853d(TAG, String.format("on cancel: connection is closed.", new Object[0]));
    }

    @Override // com.tencent.cloud.stream.tts.core.p032ws.ConnectionListener
    public void onMessage(String message) {
        if (message == null || message.trim().length() == 0) {
            return;
        }
        String str = TAG;
        AAILogger.m1853d(str, "on message:" + message);
        SpeechSynthesizerResponse speechSynthesizerResponse = (SpeechSynthesizerResponse) new Gson().fromJson(message, SpeechSynthesizerResponse.class);
        if (isRecReady(speechSynthesizerResponse)) {
            onSynthesisStart(speechSynthesizerResponse);
            this.synthesizer.markReady();
            return;
        }
        if (isRecResult(speechSynthesizerResponse) && speechSynthesizerResponse.getResult() != null) {
            onTextResult(speechSynthesizerResponse);
            return;
        }
        if (isRecComplete(speechSynthesizerResponse)) {
            onSynthesisEnd(speechSynthesizerResponse);
            this.synthesizer.markComplete();
        } else if (isTaskFailed(speechSynthesizerResponse)) {
            onSynthesisFail(speechSynthesizerResponse);
            this.synthesizer.markFail();
        } else {
            AAILogger.m1854e(str, message);
        }
    }

    @Override // com.tencent.cloud.stream.tts.core.p032ws.ConnectionListener
    public void onMessage(ByteBuffer message) {
        onAudioResult(message);
    }

    private boolean isRecReady(SpeechSynthesizerResponse response) {
        if (response.getCode().intValue() != 0 || !Objects.equals(this.status, "init") || response.getEnd().intValue() == 1) {
            return false;
        }
        this.status = "rec";
        return true;
    }

    private boolean isRecResult(SpeechSynthesizerResponse response) {
        return response.getCode().intValue() == 0 && Objects.equals(this.status, "rec") && response.getEnd().intValue() != 1;
    }

    private boolean isRecComplete(SpeechSynthesizerResponse response) {
        if (response.getCode().intValue() != 0 || response.getEnd().intValue() != 1) {
            return false;
        }
        this.status = "complete";
        return true;
    }

    private boolean isTaskFailed(SpeechSynthesizerResponse response) {
        if (response.getCode().intValue() == 0) {
            return false;
        }
        this.status = "failed";
        return true;
    }
}
