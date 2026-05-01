package com.tencent.cloud.stream.tts;

import com.aivox.base.util.SPUtil;
import com.google.gson.annotations.SerializedName;
import com.microsoft.azure.storage.table.TableConstants;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechSynthesizerResponse {

    @SerializedName(TableConstants.ErrorConstants.ERROR_CODE)
    private Integer code;

    @SerializedName("final")
    private Integer end;

    @SerializedName("message")
    private String message;

    @SerializedName("message_id")
    private String messageId;

    @SerializedName("request_id")
    private String requestId;

    @SerializedName("result")
    private SpeechSynthesizerResult result;

    @SerializedName(SPUtil.SESSION_ID)
    private String sessionId;

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getEnd() {
        return this.end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public SpeechSynthesizerResult getResult() {
        return this.result;
    }

    public void setResult(SpeechSynthesizerResult result) {
        this.result = result;
    }
}
