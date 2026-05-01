package com.aivox.common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.aivox.common_ui.databinding.DialogTitleItemViewLayoutBinding;

/* JADX INFO: loaded from: classes.dex */
public class DialogTitleItemView extends FrameLayout {
    private DialogTitleItemViewLayoutBinding mBinding;

    public DialogTitleItemView(Context context) {
        this(context, null);
    }

    public DialogTitleItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DialogTitleItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (isInEditMode()) {
            return;
        }
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mBinding = DialogTitleItemViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.DialogTitleItemView);
        this.mBinding.tvItemTitle.setText(typedArrayObtainStyledAttributes.getString(C1034R.styleable.DialogTitleItemView_dtiv_title));
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(C1034R.styleable.DialogTitleItemView_dtiv_rightIcon, 0);
        if (resourceId != 0) {
            this.mBinding.ivItemRightIcon.setVisibility(0);
            this.mBinding.ivItemRightIcon.setImageResource(resourceId);
        } else {
            this.mBinding.ivItemRightIcon.setVisibility(8);
        }
    }

    public void setText(String str) {
        this.mBinding.tvItemTitle.setText(str);
    }

    public void updateLimitText(String str) {
        this.mBinding.tvItemLimit.setText(str);
    }
}
