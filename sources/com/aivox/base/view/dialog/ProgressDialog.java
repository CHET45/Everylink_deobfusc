package com.aivox.base.view.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.aivox.base.C0874R;

/* JADX INFO: loaded from: classes.dex */
public class ProgressDialog {
    private LoadingDialog dialog;
    private TextView tvMessage;

    public void showDialog(Context context, String str) {
        showDialog(context, str, null);
    }

    public void showDialog(Context context, String str, DialogInterface.OnCancelListener onCancelListener) {
        if (this.dialog == null) {
            LoadingDialog loadingDialog = new LoadingDialog(context, C0874R.style.LoadingDialog);
            this.dialog = loadingDialog;
            loadingDialog.setContentView(C0874R.layout.layout_loadingdialog);
            this.tvMessage = (TextView) this.dialog.findViewById(C0874R.id.message);
            if (!TextUtils.isEmpty(str)) {
                this.tvMessage.setText(str);
            } else {
                this.tvMessage.setVisibility(8);
            }
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.tvMessage.getLayoutParams();
            layoutParams.width = dp2px(context, 130.0f);
            layoutParams.height = -2;
            this.tvMessage.setLayoutParams(layoutParams);
            this.tvMessage.setGravity(17);
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setCancelable(true);
            this.dialog.setOnCancelListener(onCancelListener);
            this.dialog.getWindow().getAttributes().gravity = 17;
            WindowManager.LayoutParams attributes = this.dialog.getWindow().getAttributes();
            attributes.dimAmount = 0.2f;
            this.dialog.getWindow().setAttributes(attributes);
            this.dialog.show();
        }
        this.tvMessage.setText(str);
    }

    public void hideDialog() {
        LoadingDialog loadingDialog = this.dialog;
        if (loadingDialog == null || !loadingDialog.isShowing()) {
            return;
        }
        this.dialog.dismiss();
    }

    public void showDialogView() {
        LoadingDialog loadingDialog = this.dialog;
        if (loadingDialog != null) {
            loadingDialog.show();
        }
    }

    public LoadingDialog getDialog() {
        return this.dialog;
    }

    public void setMessage(String str) {
        if (this.tvMessage == null || TextUtils.isEmpty(str)) {
            return;
        }
        this.tvMessage.setText(str);
    }

    public static int dp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
