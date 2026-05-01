package com.aivox.app.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.aivox.app.databinding.AudioOperateViewBinding;
import com.aivox.base.util.LayoutUtil;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;

/* JADX INFO: loaded from: classes.dex */
public class AudioOperateView extends BaseDialogViewWrapper {
    private final AudioOperateViewBinding mBinding;
    private final boolean mList1Empty;
    private View.OnClickListener mOnClickListener;

    public AudioOperateView(Context context) {
        this(context, true);
    }

    public AudioOperateView(Context context, boolean z) {
        super(context);
        this.mList1Empty = z;
        AudioOperateViewBinding audioOperateViewBindingInflate = AudioOperateViewBinding.inflate(LayoutInflater.from(context), this, true);
        this.mBinding = audioOperateViewBindingInflate;
        audioOperateViewBindingInflate.tvExportAudio.setAlpha(z ? 0.4f : 1.0f);
        audioOperateViewBindingInflate.tvExportFile.setAlpha(z ? 0.4f : 1.0f);
        audioOperateViewBindingInflate.tvEditFile.setAlpha(z ? 0.4f : 1.0f);
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }

    public void setItemClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
        this.mBinding.tvDelete.setOnClickListener(this.mOnClickListener);
        this.mBinding.tvShareToUser.setOnClickListener(this.mOnClickListener);
        this.mBinding.tvCopyLink.setOnClickListener(this.mOnClickListener);
        if (this.mList1Empty) {
            LayoutUtil.isBtnCanClick(false, this.mBinding.tvExportAudio, this.mBinding.tvExportFile, this.mBinding.tvEditFile);
            return;
        }
        this.mBinding.tvExportAudio.setOnClickListener(this.mOnClickListener);
        this.mBinding.tvExportFile.setOnClickListener(this.mOnClickListener);
        this.mBinding.tvEditFile.setOnClickListener(this.mOnClickListener);
    }
}
