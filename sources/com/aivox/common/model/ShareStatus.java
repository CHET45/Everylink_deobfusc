package com.aivox.common.model;

import kotlin.Metadata;

/* JADX INFO: compiled from: ShareStatus.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0018\u001a\u00020\u0010H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014¨\u0006\u0019"}, m1901d2 = {"Lcom/aivox/common/model/ShareStatus;", "", "()V", "burned", "", "getBurned", "()I", "setBurned", "(I)V", "dueDate", "getDueDate", "setDueDate", "hasPsw", "getHasPsw", "setHasPsw", "psw", "", "getPsw", "()Ljava/lang/String;", "setPsw", "(Ljava/lang/String;)V", "sign", "getSign", "setSign", "toString", "common_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class ShareStatus {
    private int burned;
    private int dueDate;
    private int hasPsw;
    private String psw;
    private String sign;

    public final int getDueDate() {
        return this.dueDate;
    }

    public final void setDueDate(int i) {
        this.dueDate = i;
    }

    public final int getHasPsw() {
        return this.hasPsw;
    }

    public final void setHasPsw(int i) {
        this.hasPsw = i;
    }

    public final int getBurned() {
        return this.burned;
    }

    public final void setBurned(int i) {
        this.burned = i;
    }

    public final String getPsw() {
        return this.psw;
    }

    public final void setPsw(String str) {
        this.psw = str;
    }

    public final String getSign() {
        return this.sign;
    }

    public final void setSign(String str) {
        this.sign = str;
    }

    public String toString() {
        return "ShareStatus(dueDate=" + this.dueDate + ", hasPsw=" + this.hasPsw + ", burned=" + this.burned + ", psw=" + this.psw + ", sign=" + this.sign + ')';
    }
}
