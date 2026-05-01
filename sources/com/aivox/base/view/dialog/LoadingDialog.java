package com.aivox.base.view.dialog;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.Key;
import com.aivox.base.C0874R;
import com.aivox.base.util.BaseAppUtils;

/* JADX INFO: loaded from: classes.dex */
public class LoadingDialog extends Dialog {
    private LoadingDialog dialog;
    private ObjectAnimator mAnim;
    private final Activity mParentActivity;
    private TextView tvMsg;

    public void setShowMsg() {
        LoadingDialog loadingDialog = this.dialog;
        if (loadingDialog == null) {
            return;
        }
        TextView textView = (TextView) loadingDialog.findViewById(C0874R.id.message);
        this.tvMsg = textView;
        textView.setVisibility(0);
    }

    public void setMsg(String str) {
        LoadingDialog loadingDialog = this.dialog;
        if (loadingDialog == null) {
            return;
        }
        TextView textView = (TextView) loadingDialog.findViewById(C0874R.id.message);
        this.tvMsg = textView;
        textView.setText(str);
    }

    public LoadingDialog(Context context) {
        super(context);
        this.mParentActivity = (Activity) context;
    }

    public LoadingDialog(Context context, int i) {
        super(context, i);
        this.mParentActivity = (Activity) context;
    }

    public LoadingDialog showDialog(Context context) {
        return showDialog(context, "加载中", true, null);
    }

    public LoadingDialog showDialog(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        return showDialog(context, null, z, onCancelListener);
    }

    public LoadingDialog showDialog(Context context, CharSequence charSequence, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        if (!BaseAppUtils.isContextExisted(context)) {
            return null;
        }
        dismissDialog();
        LoadingDialog loadingDialog = new LoadingDialog(context, C0874R.style.LoadingDialog);
        this.dialog = loadingDialog;
        loadingDialog.setContentView(C0874R.layout.layout_loadingdialog);
        this.tvMsg = (TextView) this.dialog.findViewById(C0874R.id.message);
        if (!TextUtils.isEmpty(charSequence)) {
            this.tvMsg.setText(charSequence);
        } else {
            this.tvMsg.setVisibility(8);
        }
        this.tvMsg.setVisibility(8);
        this.tvMsg.post(new Runnable() { // from class: com.aivox.base.view.dialog.LoadingDialog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2441lambda$showDialog$0$comaivoxbaseviewdialogLoadingDialog();
            }
        });
        this.dialog.setCanceledOnTouchOutside(z);
        this.dialog.setCancelable(z);
        this.dialog.setOnCancelListener(onCancelListener);
        this.dialog.getWindow().getAttributes().gravity = 17;
        WindowManager.LayoutParams attributes = this.dialog.getWindow().getAttributes();
        attributes.dimAmount = 0.4f;
        this.dialog.getWindow().setAttributes(attributes);
        this.dialog.show();
        return this.dialog;
    }

    /* JADX INFO: renamed from: lambda$showDialog$0$com-aivox-base-view-dialog-LoadingDialog, reason: not valid java name */
    /* synthetic */ void m2441lambda$showDialog$0$comaivoxbaseviewdialogLoadingDialog() {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.dialog.findViewById(C0874R.id.iv_loading), Key.ROTATION, 0.0f, 360.0f);
        this.mAnim = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setDuration(800L);
        this.mAnim.setInterpolator(new LinearInterpolator());
        this.mAnim.setRepeatCount(-1);
        this.mAnim.start();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        if (this.mAnim != null) {
            this.tvMsg.post(new Runnable() { // from class: com.aivox.base.view.dialog.LoadingDialog$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2439lambda$dismiss$1$comaivoxbaseviewdialogLoadingDialog();
                }
            });
        }
        Activity activity = this.mParentActivity;
        if (activity == null || activity.isFinishing()) {
            return;
        }
        super.dismiss();
    }

    /* JADX INFO: renamed from: lambda$dismiss$1$com-aivox-base-view-dialog-LoadingDialog, reason: not valid java name */
    /* synthetic */ void m2439lambda$dismiss$1$comaivoxbaseviewdialogLoadingDialog() {
        this.mAnim.cancel();
    }

    public void dismissDialog() {
        if (this.mAnim != null) {
            this.tvMsg.post(new Runnable() { // from class: com.aivox.base.view.dialog.LoadingDialog$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2440lambda$dismissDialog$2$comaivoxbaseviewdialogLoadingDialog();
                }
            });
        }
        LoadingDialog loadingDialog = this.dialog;
        if (loadingDialog == null || !loadingDialog.isShowing()) {
            return;
        }
        this.dialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$dismissDialog$2$com-aivox-base-view-dialog-LoadingDialog, reason: not valid java name */
    /* synthetic */ void m2440lambda$dismissDialog$2$comaivoxbaseviewdialogLoadingDialog() {
        this.mAnim.cancel();
    }
}
