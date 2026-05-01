package com.github.houbb.heaven.response.exception;

import com.github.houbb.heaven.response.respcode.AdviceRespCode;
import com.github.houbb.heaven.response.respcode.RespCode;

/* JADX INFO: loaded from: classes3.dex */
public class GenericAdviceRuntimeException extends GenericRuntimeException implements AdviceRespCode {
    private final String advice;

    public GenericAdviceRuntimeException(RespCode respCode, String str) {
        super(respCode);
        this.advice = str;
    }

    public GenericAdviceRuntimeException(String str, RespCode respCode, String str2) {
        super(str, respCode);
        this.advice = str2;
    }

    public GenericAdviceRuntimeException(String str, Throwable th, RespCode respCode, String str2) {
        super(str, th, respCode);
        this.advice = str2;
    }

    public GenericAdviceRuntimeException(Throwable th, RespCode respCode, String str) {
        super(th, respCode);
        this.advice = str;
    }

    public GenericAdviceRuntimeException(String str, Throwable th, boolean z, boolean z2, RespCode respCode, String str2) {
        super(str, th, z, z2, respCode);
        this.advice = str2;
    }

    public GenericAdviceRuntimeException(AdviceRespCode adviceRespCode) {
        super(adviceRespCode);
        this.advice = adviceRespCode.getAdvice();
    }

    public GenericAdviceRuntimeException(String str, AdviceRespCode adviceRespCode) {
        super(str, adviceRespCode);
        this.advice = adviceRespCode.getAdvice();
    }

    public GenericAdviceRuntimeException(String str, Throwable th, AdviceRespCode adviceRespCode) {
        super(str, th, adviceRespCode);
        this.advice = adviceRespCode.getAdvice();
    }

    public GenericAdviceRuntimeException(Throwable th, AdviceRespCode adviceRespCode) {
        super(th, adviceRespCode);
        this.advice = adviceRespCode.getAdvice();
    }

    public GenericAdviceRuntimeException(String str, Throwable th, boolean z, boolean z2, AdviceRespCode adviceRespCode) {
        super(str, th, z, z2, adviceRespCode);
        this.advice = adviceRespCode.getAdvice();
    }

    @Override // com.github.houbb.heaven.response.respcode.AdviceRespCode
    public String getAdvice() {
        return this.advice;
    }
}
