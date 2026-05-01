package com.aivox.account.activity;

import com.aivox.common.util.AppUtils;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class CodeInputActivity$$ExternalSyntheticLambda3 implements AppUtils.ResponseErrorCallback {
    public final /* synthetic */ CodeInputActivity f$0;

    public /* synthetic */ CodeInputActivity$$ExternalSyntheticLambda3(CodeInputActivity codeInputActivity) {
        this.f$0 = codeInputActivity;
    }

    @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
    public final void callback() {
        this.f$0.verifyCode();
    }
}
