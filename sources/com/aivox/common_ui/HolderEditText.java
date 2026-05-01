package com.aivox.common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.aivox.common_ui.databinding.HolderEditTextLayoutBinding;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;

/* JADX INFO: loaded from: classes.dex */
public class HolderEditText extends FrameLayout {
    private float mAvailableWidth;
    private HolderEditTextLayoutBinding mBinding;
    private TextChangeListener mChangeListener;
    private float mOriginalTextSize;

    public interface TextChangeListener {
        void onTextChanged(CharSequence charSequence);
    }

    public HolderEditText(Context context) {
        this(context, null);
    }

    public HolderEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HolderEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (isInEditMode()) {
            return;
        }
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        HolderEditTextLayoutBinding holderEditTextLayoutBindingInflate = HolderEditTextLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        this.mBinding = holderEditTextLayoutBindingInflate;
        this.mOriginalTextSize = holderEditTextLayoutBindingInflate.etContent.getTextSize();
        this.mAvailableWidth = ScreenUtils.getScreenWidth() - SizeUtils.dp2px(44);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.HolderEditText);
        this.mBinding.tvHolder.setText(typedArrayObtainStyledAttributes.getString(C1034R.styleable.HolderEditText_het_hint));
        int i = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.HolderEditText_het_inputType, 0);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.HolderEditText_het_showKeyboard, false);
        final boolean z2 = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.HolderEditText_het_autoResizeEnable, false);
        final int dimension = (int) typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.HolderEditText_het_autoResizeMin, SizeUtils.sp2px(16.0f));
        if (i == 0) {
            this.mBinding.etContent.setInputType(1);
        } else if (i == 1) {
            this.mBinding.etContent.setInputType(32);
        } else if (i == 2) {
            this.mBinding.etContent.setInputType(2);
        } else if (i == 3) {
            this.mBinding.etContent.setInputType(129);
            this.mBinding.etContent.setTypeface(Typeface.DEFAULT);
        }
        this.mBinding.tvHolder.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.HolderEditText$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2506lambda$init$0$comaivoxcommon_uiHolderEditText(view2);
            }
        });
        this.mBinding.etContent.addTextChangedListener(new TextWatcher() { // from class: com.aivox.common_ui.HolderEditText.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (HolderEditText.this.mChangeListener != null) {
                    HolderEditText.this.mChangeListener.onTextChanged(editable);
                }
                HolderEditText.this.mBinding.tvHolder.setVisibility(TextUtils.isEmpty(editable) ? 0 : 8);
                if (z2) {
                    Paint paint = new Paint();
                    paint.setTextSize(HolderEditText.this.mOriginalTextSize);
                    while (paint.measureText(editable.toString()) > HolderEditText.this.mAvailableWidth && paint.getTextSize() >= dimension) {
                        paint.setTextSize(paint.getTextSize() - 1.0f);
                    }
                    HolderEditText.this.mBinding.etContent.setTextSize(SizeUtils.px2sp(paint.getTextSize()));
                }
            }
        });
        if (z) {
            this.mBinding.etContent.requestFocus();
            KeyboardUtils.showSoftInput(this.mBinding.etContent);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX INFO: renamed from: lambda$init$0$com-aivox-common_ui-HolderEditText, reason: not valid java name */
    /* synthetic */ void m2506lambda$init$0$comaivoxcommon_uiHolderEditText(View view2) {
        this.mBinding.etContent.requestFocus();
        KeyboardUtils.showSoftInput(this.mBinding.etContent);
    }

    public void addTextChangeListener(TextChangeListener textChangeListener) {
        this.mChangeListener = textChangeListener;
    }

    public String getText() {
        return this.mBinding.etContent.getText().toString();
    }

    public void setText(CharSequence charSequence) {
        this.mBinding.etContent.setText(charSequence);
    }

    public void setHint(CharSequence charSequence) {
        this.mBinding.tvHolder.setText(charSequence);
    }

    public void setInputType(int i) {
        this.mBinding.etContent.setInputType(i);
        this.mBinding.etContent.setTypeface(Typeface.DEFAULT);
    }
}
