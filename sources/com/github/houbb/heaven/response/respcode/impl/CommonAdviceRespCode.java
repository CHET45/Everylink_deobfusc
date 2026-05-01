package com.github.houbb.heaven.response.respcode.impl;

import com.github.houbb.heaven.response.respcode.AdviceRespCode;
import com.github.houbb.heaven.response.respcode.RespCode;

/* JADX INFO: loaded from: classes3.dex */
public enum CommonAdviceRespCode implements AdviceRespCode {
    ;

    private final String advice;
    private final String code;
    private final String msg;

    CommonAdviceRespCode(String str, String str2, String str3) {
        this.code = str;
        this.msg = str2;
        this.advice = str3;
    }

    CommonAdviceRespCode(RespCode respCode, String str) {
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
        this.advice = str;
    }

    @Override // com.github.houbb.heaven.response.respcode.AdviceRespCode
    public String getAdvice() {
        return this.advice;
    }

    @Override // com.github.houbb.heaven.response.respcode.RespCode
    public String getCode() {
        return this.code;
    }

    @Override // com.github.houbb.heaven.response.respcode.RespCode
    public String getMsg() {
        return this.msg;
    }

    @Override // java.lang.Enum
    public String toString() {
        return "CommonAdviceRespCode{code='" + this.code + "', msg='" + this.msg + "', advice='" + this.advice + "'}";
    }
}
