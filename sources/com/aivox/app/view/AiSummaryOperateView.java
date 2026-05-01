package com.aivox.app.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.aivox.app.databinding.AiSummaryOperateViewBinding;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;

/* JADX INFO: loaded from: classes.dex */
public class AiSummaryOperateView extends BaseDialogViewWrapper {
    private final AiSummaryOperateViewBinding mBinding;

    public AiSummaryOperateView(Context context, String str) {
        super(context);
        this.mBinding = AiSummaryOperateViewBinding.inflate(LayoutInflater.from(context), this, true);
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }

    public void setItemClickListener(View.OnClickListener onClickListener) {
        this.mBinding.tvSummarySelect.setOnClickListener(onClickListener);
    }
}
