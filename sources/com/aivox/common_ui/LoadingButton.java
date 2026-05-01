package com.aivox.common_ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.motion.widget.Key;
import com.aivox.base.C0874R;
import com.aivox.common_ui.databinding.LoadingButtonLayoutBinding;
import com.blankj.utilcode.util.ColorUtils;

/* JADX INFO: loaded from: classes.dex */
public class LoadingButton extends FrameLayout {
    private static final int TYPE_HIGHLIGHT = 3;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_STROKE = 1;
    private static final int TYPE_WARNING = 2;
    private ObjectAnimator mAnim;
    private LoadingButtonLayoutBinding mBinding;
    private boolean mIsLoading;
    private int mTextColor;

    public LoadingButton(Context context) {
        this(context, null);
    }

    public LoadingButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LoadingButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsLoading = false;
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mBinding = LoadingButtonLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.LoadingButton);
        String string = typedArrayObtainStyledAttributes.getString(C1034R.styleable.LoadingButton_lb_text);
        int i = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.LoadingButton_lb_textSize, 17);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.LoadingButton_lb_enable, true);
        int i2 = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.LoadingButton_lb_buttonType, 0);
        this.mBinding.tvBtn.setTextSize(i);
        if (i2 == 0) {
            this.mBinding.getRoot().setBackground(AppCompatResources.getDrawable(context, C1034R.drawable.bg_btn_normal));
            this.mBinding.tvBtn.setTextColor(context.getColor(C0874R.color.txt_color_secondary));
        } else if (i2 == 1) {
            this.mBinding.getRoot().setBackground(AppCompatResources.getDrawable(context, C1034R.drawable.bg_btn_stroke));
            this.mBinding.tvBtn.setTextColor(context.getColor(C0874R.color.txt_color_primary));
        } else if (i2 == 2) {
            this.mBinding.getRoot().setBackground(AppCompatResources.getDrawable(context, C1034R.drawable.bg_btn_warning));
            this.mBinding.tvBtn.setTextColor(context.getColor(C0874R.color.txt_color_secondary));
        } else if (i2 == 3) {
            this.mBinding.getRoot().setBackground(AppCompatResources.getDrawable(context, C1034R.drawable.bg_btn_highlight));
            this.mBinding.tvBtn.setTextColor(context.getColor(C0874R.color.txt_color_primary));
        }
        this.mTextColor = this.mBinding.tvBtn.getCurrentTextColor();
        setText(string);
        setEnabled(z);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mBinding.ivLoading, Key.ROTATION, 0.0f, 360.0f);
        this.mAnim = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setDuration(800L);
        this.mAnim.setInterpolator(new LinearInterpolator());
        this.mAnim.setRepeatCount(-1);
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setText(String str) {
        this.mBinding.tvBtn.setText(str);
    }

    public void startLoading() {
        this.mIsLoading = true;
        this.mBinding.tvBtn.setVisibility(4);
        this.mBinding.ivLoading.setVisibility(0);
        this.mAnim.start();
    }

    public void stopLoading() {
        this.mIsLoading = false;
        this.mAnim.cancel();
        this.mBinding.tvBtn.setVisibility(0);
        this.mBinding.ivLoading.setVisibility(8);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mBinding.getRoot().setEnabled(z);
        this.mBinding.tvBtn.setTextColor(z ? this.mTextColor : ColorUtils.getColor(C0874R.color.txt_color_tertiary));
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mIsLoading) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAnim.cancel();
    }
}
