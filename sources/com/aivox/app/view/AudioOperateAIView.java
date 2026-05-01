package com.aivox.app.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.aivox.app.databinding.AudioOperateAiViewBinding;
import com.aivox.base.common.MyEnum;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;

/* JADX INFO: loaded from: classes.dex */
public class AudioOperateAIView extends BaseDialogViewWrapper {
    private final AudioOperateAiViewBinding mBinding;

    public AudioOperateAIView(Context context, int i) {
        super(context);
        AudioOperateAiViewBinding audioOperateAiViewBindingInflate = AudioOperateAiViewBinding.inflate(LayoutInflater.from(context), this, true);
        this.mBinding = audioOperateAiViewBindingInflate;
        if (i == MyEnum.TRANSLATE_LANGUAGE.ZH.type || i == MyEnum.TRANSLATE_LANGUAGE.EN.type) {
            return;
        }
        audioOperateAiViewBindingInflate.tvAiSpeaker.setVisibility(8);
        audioOperateAiViewBindingInflate.lineSpeaker.setVisibility(8);
    }

    public void setItemClickListener(View.OnClickListener onClickListener) {
        this.mBinding.tvAiSpeaker.setOnClickListener(onClickListener);
        this.mBinding.tvTranscribe.setOnClickListener(onClickListener);
        this.mBinding.tvTranslate.setOnClickListener(onClickListener);
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }
}
