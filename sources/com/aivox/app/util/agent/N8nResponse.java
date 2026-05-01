package com.aivox.app.util.agent;

import kotlin.Metadata;

/* JADX INFO: compiled from: N8nData.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u001b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0016\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0006\"\u0004\b\u0018\u0010\bR\u001c\u0010\u0019\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\f\"\u0004\b\u001b\u0010\u000eR\u001c\u0010\u001c\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\f\"\u0004\b\u001e\u0010\u000eR\u001c\u0010\u001f\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\f\"\u0004\b!\u0010\u000eR\u001a\u0010\"\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0006\"\u0004\b$\u0010\bR\u001c\u0010%\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\f\"\u0004\b'\u0010\u000eR\u001c\u0010(\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\f\"\u0004\b*\u0010\u000e¨\u0006+"}, m1901d2 = {"Lcom/aivox/app/util/agent/N8nResponse;", "", "()V", "AskLocation", "", "getAskLocation", "()Z", "setAskLocation", "(Z)V", "action", "", "getAction", "()Ljava/lang/String;", "setAction", "(Ljava/lang/String;)V", "cmd", "", "getCmd", "()Ljava/lang/Integer;", "setCmd", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "error", "getError", "setError", "keyword", "getKeyword", "setKeyword", "reason", "getReason", "setReason", "reply", "getReply", "setReply", "sortDistance", "getSortDistance", "setSortDistance", "summary", "getSummary", "setSummary", "time", "getTime", "setTime", "app_everylinkRelease"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class N8nResponse {
    private boolean AskLocation;
    private String action;
    private Integer cmd;
    private boolean error;
    private String keyword;
    private String reason;
    private String reply;
    private boolean sortDistance;
    private String summary;
    private String time;

    public final String getReply() {
        return this.reply;
    }

    public final void setReply(String str) {
        this.reply = str;
    }

    public final String getAction() {
        return this.action;
    }

    public final void setAction(String str) {
        this.action = str;
    }

    public final Integer getCmd() {
        return this.cmd;
    }

    public final void setCmd(Integer num) {
        this.cmd = num;
    }

    public final String getReason() {
        return this.reason;
    }

    public final void setReason(String str) {
        this.reason = str;
    }

    public final String getSummary() {
        return this.summary;
    }

    public final void setSummary(String str) {
        this.summary = str;
    }

    public final String getTime() {
        return this.time;
    }

    public final void setTime(String str) {
        this.time = str;
    }

    public final boolean getError() {
        return this.error;
    }

    public final void setError(boolean z) {
        this.error = z;
    }

    public final boolean getAskLocation() {
        return this.AskLocation;
    }

    public final void setAskLocation(boolean z) {
        this.AskLocation = z;
    }

    public final boolean getSortDistance() {
        return this.sortDistance;
    }

    public final void setSortDistance(boolean z) {
        this.sortDistance = z;
    }

    public final String getKeyword() {
        return this.keyword;
    }

    public final void setKeyword(String str) {
        this.keyword = str;
    }
}
