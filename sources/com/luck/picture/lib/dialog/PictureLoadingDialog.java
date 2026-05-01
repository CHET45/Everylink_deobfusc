package com.luck.picture.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import com.luck.picture.lib.C2114R;

/* JADX INFO: loaded from: classes3.dex */
public class PictureLoadingDialog extends Dialog {
    public PictureLoadingDialog(Context context) {
        super(context, C2114R.style.Picture_Theme_AlertDialog);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C2114R.layout.ps_alert_dialog);
        setDialogSize();
    }

    private void setDialogSize() {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = -2;
        attributes.height = -2;
        attributes.gravity = 17;
        getWindow().setWindowAnimations(C2114R.style.PictureThemeDialogWindowStyle);
        getWindow().setAttributes(attributes);
    }
}
