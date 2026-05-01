package com.aivox.app.util.agent;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CozeData.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R$\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\n"}, m1901d2 = {"Lcom/aivox/app/util/agent/SubmitToolOutputs;", "", "()V", "toolCalls", "", "Lcom/aivox/app/util/agent/ToolCall;", "getToolCalls", "()Ljava/util/List;", "setToolCalls", "(Ljava/util/List;)V", "app_everylinkRelease"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class SubmitToolOutputs {

    @SerializedName("tool_calls")
    private List<ToolCall> toolCalls = new ArrayList();

    public final List<ToolCall> getToolCalls() {
        return this.toolCalls;
    }

    public final void setToolCalls(List<ToolCall> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.toolCalls = list;
    }
}
