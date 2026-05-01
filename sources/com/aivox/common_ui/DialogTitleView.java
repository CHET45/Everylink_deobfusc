package com.aivox.common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.aivox.common_ui.databinding.DialogTitleViewLayoutBinding;

/* JADX INFO: loaded from: classes.dex */
public class DialogTitleView extends FrameLayout {
    private DialogTitleViewLayoutBinding mBinding;

    public DialogTitleView(Context context) {
        this(context, null);
    }

    public DialogTitleView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DialogTitleView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (isInEditMode()) {
            return;
        }
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mBinding = DialogTitleViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.DialogTitleView);
        this.mBinding.tvTitle.setText(typedArrayObtainStyledAttributes.getString(C1034R.styleable.DialogTitleView_dtv_title));
        this.mBinding.ivInfo.setVisibility(typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.DialogTitleView_dtv_show_info, false) ? 0 : 8);
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setTitle(String str) {
        this.mBinding.tvTitle.setText(str);
    }

    public void setViewClickListener(View.OnClickListener onClickListener) {
        this.mBinding.ivClose.setOnClickListener(onClickListener);
        this.mBinding.ivInfo.setOnClickListener(onClickListener);
    }
}
