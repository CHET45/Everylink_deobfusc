package com.aivox.base.statemachine;

/* JADX INFO: loaded from: classes.dex */
public enum BaseStateCode implements BaseIStateCode {
    UNINITIALIZED(999, "未初始化"),
    ENDED(9999, "已经终止");

    private Integer code;
    private String msg;

    BaseStateCode(Integer num, String str) {
        this.code = num;
        this.msg = str;
    }

    @Override // com.aivox.base.statemachine.BaseIStateCode
    public Integer getCode() {
        return this.code;
    }

    @Override // com.aivox.base.statemachine.BaseIStateCode
    public void setCode(Integer num) {
        this.code = num;
    }

    @Override // com.aivox.base.statemachine.BaseIStateCode
    public String getMsg() {
        return this.msg;
    }

    @Override // com.aivox.base.statemachine.BaseIStateCode
    public void setMsg(String str) {
        this.msg = str;
    }
}
