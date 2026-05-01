package com.aivox.common.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.aivox.base.C0874R;
import com.aivox.common.C0958R;
import com.aivox.common.databinding.ViewUploadProgressYellowBinding;
import com.github.houbb.heaven.constant.PunctuationConst;

/* JADX INFO: loaded from: classes.dex */
public class UploadProgressView extends ConstraintLayout {
    private final Context context;
    private final Dialog dialog;
    private ViewUploadProgressYellowBinding mBinding;

    public UploadProgressView(Context context) {
        super(context);
        this.context = context;
        View viewInflate = inflate(context, C0958R.layout.view_upload_progress_yellow, this);
        this.mBinding = ViewUploadProgressYellowBinding.inflate(LayoutInflater.from(context), this, true);
        Dialog dialog = new Dialog(context, C0874R.style.DialogTheme);
        this.dialog = dialog;
        dialog.setContentView(viewInflate);
        Window window = dialog.getWindow();
        if (window != null) {
            Activity activity = (Activity) context;
            if (activity.isFinishing() || activity.isDestroyed()) {
                return;
            }
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            window.setGravity(17);
            dialog.show();
        }
    }

    public void refreshProgress(int i) {
        this.mBinding.progressCircular.setProgress(i);
        this.mBinding.f202tv.setText(i + PunctuationConst.PERCENT);
        this.mBinding.tvSub.setText(i == 100 ? C0874R.string.audio_sync_success : C0874R.string.audio_sync_ing);
    }

    public void dismiss() {
        if (this.dialog == null || ((Activity) this.context).isFinishing() || ((Activity) this.context).isDestroyed()) {
            return;
        }
        this.dialog.dismiss();
    }
}
