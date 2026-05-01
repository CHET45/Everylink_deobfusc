package com.aivox.base.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.FrameLayout;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseDialogViewWrapper extends FrameLayout implements IDialogVIew {
    public Dialog mDialog;

    public BaseDialogViewWrapper(Context context) {
        super(context);
    }
}
