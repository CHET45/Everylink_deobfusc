package com.aivox.common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.aivox.common_ui.databinding.LoginEditTextLayoutBinding;
import com.blankj.utilcode.util.KeyboardUtils;

/* JADX INFO: loaded from: classes.dex */
public class LoginEditText extends FrameLayout {
    public static final int STATUS_CORRECT = 1;
    public static final int STATUS_ERROR = 2;
    public static final int STATUS_NORMAL = 0;
    private LoginEditTextLayoutBinding mBinding;
    private boolean mContentVisible;

    public LoginEditText(Context context) {
        this(context, null);
    }

    public LoginEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LoginEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContentVisible = false;
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        this.mBinding = LoginEditTextLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.LoginEditText);
        this.mBinding.etContent.setHint(typedArrayObtainStyledAttributes.getString(C1034R.styleable.LoginEditText_let_hint));
        int i = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.LoginEditText_let_inputType, 0);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.LoginEditText_let_showKeyboard, false);
        if (i == 0) {
            this.mBinding.etContent.setInputType(1);
        } else if (i == 1) {
            this.mBinding.etContent.setInputType(32);
        } else if (i == 2) {
            this.mBinding.etContent.setInputType(2);
        } else if (i == 3) {
            this.mBinding.etContent.setInputType(129);
            this.mBinding.etContent.setTypeface(Typeface.DEFAULT);
            this.mBinding.ivDisplay.setVisibility(0);
        }
        this.mBinding.ivDisplay.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.LoginEditText$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2508lambda$initView$0$comaivoxcommon_uiLoginEditText(view2);
            }
        });
        if (z) {
            this.mBinding.etContent.requestFocus();
            this.mBinding.etContent.postDelayed(new Runnable() { // from class: com.aivox.common_ui.LoginEditText.1
                @Override // java.lang.Runnable
                public void run() {
                    KeyboardUtils.showSoftInput();
                }
            }, 200L);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-common_ui-LoginEditText, reason: not valid java name */
    /* synthetic */ void m2508lambda$initView$0$comaivoxcommon_uiLoginEditText(View view2) {
        boolean z = this.mContentVisible;
        this.mContentVisible = !z;
        if (!z) {
            this.mBinding.ivDisplay.setImageResource(C1034R.drawable.ic_eye_open);
            this.mBinding.etContent.setInputType(145);
            this.mBinding.etContent.setTypeface(Typeface.DEFAULT);
        } else {
            this.mBinding.ivDisplay.setImageResource(C1034R.drawable.ic_eye_close);
            this.mBinding.etContent.setInputType(129);
            this.mBinding.etContent.setTypeface(Typeface.DEFAULT);
        }
        this.mBinding.etContent.setSelection(this.mBinding.etContent.getText().length());
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        this.mBinding.etContent.addTextChangedListener(textWatcher);
    }

    public void setHint(String str) {
        this.mBinding.etContent.setHint(str);
    }

    public void setText(String str) {
        this.mBinding.etContent.setText(str);
    }

    public String getText() {
        return this.mBinding.etContent.getText().toString();
    }

    public void setInputType(int i) {
        this.mBinding.etContent.setInputType(i);
        this.mBinding.etContent.setTypeface(Typeface.DEFAULT);
    }

    public void changeStatus(int i) {
        if (i == 0) {
            this.mBinding.ivStatus.setVisibility(8);
            this.mBinding.llContent.setBackgroundResource(C1034R.drawable.bg_login_edittext);
        } else if (i == 1) {
            this.mBinding.ivStatus.setImageResource(C1034R.drawable.ic_login_correct);
            this.mBinding.llContent.setBackgroundResource(C1034R.drawable.bg_login_edittext_correct);
            this.mBinding.ivStatus.setVisibility(0);
        } else {
            if (i != 2) {
                return;
            }
            this.mBinding.ivStatus.setImageResource(C1034R.drawable.ic_login_error);
            this.mBinding.llContent.setBackgroundResource(C1034R.drawable.bg_login_edittext_error);
            this.mBinding.ivStatus.setVisibility(0);
        }
    }
}
