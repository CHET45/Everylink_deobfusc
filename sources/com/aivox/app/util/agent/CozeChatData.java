package com.aivox.app.util.agent;

import androidx.core.app.NotificationCompat;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CozeData.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR \u0010\f\u001a\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R \u0010\u0012\u001a\u0004\u0018\u00010\u00138\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\b¨\u0006\u001b"}, m1901d2 = {"Lcom/aivox/app/util/agent/CozeChatData;", "", "()V", "conversationId", "", "getConversationId", "()Ljava/lang/String;", "setConversationId", "(Ljava/lang/String;)V", "id", "getId", "setId", "lastError", "Lcom/aivox/app/util/agent/LastError;", "getLastError", "()Lcom/aivox/app/util/agent/LastError;", "setLastError", "(Lcom/aivox/app/util/agent/LastError;)V", "requiredAction", "Lcom/aivox/app/util/agent/RequiredAction;", "getRequiredAction", "()Lcom/aivox/app/util/agent/RequiredAction;", "setRequiredAction", "(Lcom/aivox/app/util/agent/RequiredAction;)V", NotificationCompat.CATEGORY_STATUS, "getStatus", "setStatus", "app_everylinkRelease"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class CozeChatData {

    @SerializedName("last_error")
    private LastError lastError;

    @SerializedName("required_action")
    private RequiredAction requiredAction;
    private String id = "";

    @SerializedName("conversation_id")
    private String conversationId = "";
    private String status = "";

    public final String getId() {
        return this.id;
    }

    public final void setId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final String getConversationId() {
        return this.conversationId;
    }

    public final void setConversationId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.conversationId = str;
    }

    public final String getStatus() {
        return this.status;
    }

    public final void setStatus(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.status = str;
    }

    public final RequiredAction getRequiredAction() {
        return this.requiredAction;
    }

    public final void setRequiredAction(RequiredAction requiredAction) {
        this.requiredAction = requiredAction;
    }

    public final LastError getLastError() {
        return this.lastError;
    }

    public final void setLastError(LastError lastError) {
        this.lastError = lastError;
    }
}
