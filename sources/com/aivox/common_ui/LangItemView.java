package com.aivox.common_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.aivox.base.C0874R;
import com.aivox.base.util.DialogUtils;
import com.aivox.common_ui.databinding.LangItemViewLayoutBinding;

/* JADX INFO: loaded from: classes.dex */
public class LangItemView extends LinearLayout {
    private boolean enable;
    private boolean isChecked;
    private boolean isPro;
    private LangItemViewLayoutBinding mBinding;

    public LangItemView(Context context) {
        this(context, null);
    }

    public LangItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LangItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.enable = true;
        this.isPro = false;
        init(context);
    }

    private void init(final Context context) {
        LangItemViewLayoutBinding langItemViewLayoutBindingInflate = LangItemViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        this.mBinding = langItemViewLayoutBindingInflate;
        langItemViewLayoutBindingInflate.ivLangNotice.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.LangItemView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DialogUtils.showDialogWithDefBtnAndSingleListener(context, "", Integer.valueOf(C0874R.string.unlimited_lang_notice), null, false, true);
            }
        });
    }

    public void setChecked(boolean z) {
        this.isChecked = z;
        this.mBinding.ivLangChecked.setVisibility(z ? 0 : 8);
        this.mBinding.llLangContent.setBackgroundResource(z ? C1034R.drawable.bg_lang_item_selected : C1034R.drawable.bg_lang_item);
    }

    public void setText(int i) {
        this.mBinding.tvLangName.setText(i);
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean z) {
        this.enable = z;
        this.mBinding.tvLangName.setAlpha(z ? 1.0f : 0.2f);
        if (z) {
            return;
        }
        setChecked(false);
    }

    public void setPro(int i) {
        this.isPro = i >= 10000;
        this.mBinding.tvLangPro.setVisibility(this.isPro ? 0 : 8);
        this.mBinding.ivLangNotice.setVisibility(this.isPro ? 0 : 8);
    }

    public boolean isPro() {
        return this.isPro;
    }
}
