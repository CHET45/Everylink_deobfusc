package com.tencent.cloud.stream.tts.core.exception;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

/* JADX INFO: loaded from: classes4.dex */
public enum SynthesizerExceptionType {
    CLIENT_CANNOT_BE_NULL(-400, "client cannot be null"),
    CREDENTIAL_CANNOT_BE_NULL(-401, "credential cannot be null"),
    REQUEST_CANNOT_BE_NULL(-402, "request cannot be null"),
    LISTENER_CANNOT_BE_NULL(-403, "listener cannot be null"),
    APPID_IS_EMPTY(BaseMultiItemQuickAdapter.TYPE_NOT_FOUND, "appId cannot be empty"),
    SECRETID_IS_EMPTY(-405, "secretId cannot be empty"),
    SECRETKEY_IS_EMPTY(-406, "secretKey cannot be empty"),
    START_SYNTHESIZER_FAIL(-407, "fail to start synthesizer"),
    SEND_TEXT_FAIL(-408, "fail to send text"),
    CONNECT_SERVER_FAIL(-409, "fail to connect server"),
    INCORRECT_STATE(-410, "");

    final int code;
    final String message;

    SynthesizerExceptionType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
