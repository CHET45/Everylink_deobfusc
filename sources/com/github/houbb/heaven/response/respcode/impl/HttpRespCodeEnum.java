package com.github.houbb.heaven.response.respcode.impl;

import com.github.houbb.heaven.response.respcode.RespCode;

/* JADX INFO: loaded from: classes3.dex */
public enum HttpRespCodeEnum implements RespCode {
    OK(200, "OK");

    private final int code;
    private final String msg;

    HttpRespCodeEnum(int i, String str) {
        this.code = i;
        this.msg = str;
    }

    @Override // com.github.houbb.heaven.response.respcode.RespCode
    public String getCode() {
        return String.valueOf(this.code);
    }

    public int getCodeInt() {
        return this.code;
    }

    @Override // com.github.houbb.heaven.response.respcode.RespCode
    public String getMsg() {
        return this.msg;
    }
}
