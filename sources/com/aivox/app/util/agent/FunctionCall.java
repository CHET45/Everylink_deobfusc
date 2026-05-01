package com.aivox.app.util.agent;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CozeData.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b¨\u0006\f"}, m1901d2 = {"Lcom/aivox/app/util/agent/FunctionCall;", "", "()V", "arguments", "", "getArguments", "()Ljava/lang/String;", "setArguments", "(Ljava/lang/String;)V", "name", "getName", "setName", "app_everylinkRelease"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class FunctionCall {
    private String name = "";
    private String arguments = "";

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    public final String getArguments() {
        return this.arguments;
    }

    public final void setArguments(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.arguments = str;
    }
}
