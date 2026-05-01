package com.aivox.base.common;

import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.SPUtil;

/* JADX INFO: loaded from: classes.dex */
public class BaseDataHandle {
    private static BaseDataHandle ins;
    private int code;
    private String msg;
    private String uid;

    private BaseDataHandle() {
    }

    public static BaseDataHandle getIns() {
        if (ins == null) {
            ins = new BaseDataHandle();
        }
        return ins;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public String getUid() {
        if (BaseStringUtil.isEmpty(this.uid)) {
            this.uid = (String) SPUtil.get(SPUtil.USER_ID, "0");
        }
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }
}
