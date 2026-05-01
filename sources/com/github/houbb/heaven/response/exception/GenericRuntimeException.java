package com.github.houbb.heaven.response.exception;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.response.respcode.RespCode;
import com.github.houbb.heaven.util.lang.StringUtil;

/* JADX INFO: loaded from: classes3.dex */
public class GenericRuntimeException extends RuntimeException implements RespCode {
    private final RespCode respCode;

    public GenericRuntimeException(RespCode respCode) {
        this.respCode = respCode;
    }

    public GenericRuntimeException(String str, RespCode respCode) {
        super(str);
        this.respCode = respCode;
    }

    public GenericRuntimeException(String str, Throwable th, RespCode respCode) {
        super(str, th);
        this.respCode = respCode;
    }

    public GenericRuntimeException(Throwable th, RespCode respCode) {
        super(th);
        this.respCode = respCode;
    }

    public GenericRuntimeException(String str, Throwable th, boolean z, boolean z2, RespCode respCode) {
        super(str, th, z, z2);
        this.respCode = respCode;
    }

    @Override // com.github.houbb.heaven.response.respcode.RespCode
    public String getCode() {
        return this.respCode.getCode();
    }

    @Override // com.github.houbb.heaven.response.respcode.RespCode
    public String getMsg() {
        return this.respCode.getMsg();
    }

    public String getMsgMixed() {
        if (StringUtil.isNotEmpty(super.getMessage())) {
            return this.respCode.getMsg() + PunctuationConst.COMMA + super.getMessage();
        }
        return getMsg();
    }

    public String getMsgPerfer() {
        if (StringUtil.isNotEmpty(super.getMessage())) {
            return super.getMessage();
        }
        return getMsg();
    }
}
