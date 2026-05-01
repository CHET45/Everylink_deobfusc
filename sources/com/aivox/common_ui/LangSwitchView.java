package com.aivox.common_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.common_ui.databinding.LangSwitchViewLayoutBinding;

/* JADX INFO: loaded from: classes.dex */
public class LangSwitchView extends FrameLayout {
    private LangSwitchViewLayoutBinding mBinding;

    public LangSwitchView(Context context) {
        this(context, null);
    }

    public LangSwitchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LangSwitchView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (isInEditMode()) {
            return;
        }
        init(context);
    }

    private void init(Context context) {
        this.mBinding = LangSwitchViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
    }

    public void setType(int i) {
        if (BaseAppUtils.getMainTransType(i) == 2) {
            this.mBinding.groupTranslate.setVisibility(0);
            this.mBinding.tvTranscribe.setVisibility(4);
        } else {
            this.mBinding.groupTranslate.setVisibility(4);
            this.mBinding.tvTranscribe.setVisibility(0);
        }
    }

    public void setLanguage(String str, String str2) {
        this.mBinding.tvTranscribe.setText(str);
        this.mBinding.tvLangFrom.setText(str);
        this.mBinding.tvLangTo.setText(str2);
    }
}
