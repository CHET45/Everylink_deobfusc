package com.tencent.cos.xml.model.tag;

import com.tencent.cos.xml.utils.DigestUtils;

/* JADX INFO: loaded from: classes4.dex */
public class CallbackResult {
    public String callbackBody;
    public boolean callbackBodyNotBase64;
    public Error error;
    public String status;

    public static class Error {
        public String code;
        public String message;
    }

    public String getCallbackBody() {
        if (this.callbackBodyNotBase64) {
            return this.callbackBody;
        }
        return DigestUtils.decodeBase64(this.callbackBody);
    }
}
