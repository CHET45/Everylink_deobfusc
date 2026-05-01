package com.aivox.common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.common_ui.databinding.DialogEditItemViewLayoutBinding;
import com.blankj.utilcode.util.KeyboardUtils;

/* JADX INFO: loaded from: classes.dex */
public class DialogEditItemView extends FrameLayout {
    private DialogEditItemViewLayoutBinding mBinding;

    public DialogEditItemView(Context context) {
        this(context, null);
    }

    public DialogEditItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DialogEditItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (isInEditMode()) {
            return;
        }
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mBinding = DialogEditItemViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.DialogEditItemView);
        this.mBinding.tvItemTitle.setText(typedArrayObtainStyledAttributes.getString(C1034R.styleable.DialogEditItemView_deiv_title));
        this.mBinding.etItemContent.setHint(typedArrayObtainStyledAttributes.getString(C1034R.styleable.DialogEditItemView_deiv_hint));
        typedArrayObtainStyledAttributes.recycle();
    }

    public void getFocus() {
        this.mBinding.etItemContent.requestFocus();
        this.mBinding.etItemContent.postDelayed(new Runnable() { // from class: com.aivox.common_ui.DialogEditItemView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                KeyboardUtils.showSoftInput();
            }
        }, 500L);
    }

    public String getText() {
        String string = this.mBinding.etItemContent.getText().toString();
        return BaseStringUtil.isEmpty(string) ? "" : string;
    }

    public void setText(String str) {
        this.mBinding.etItemContent.setText(str);
    }
}
