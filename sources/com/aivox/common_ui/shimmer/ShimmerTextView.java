package com.aivox.common_ui.shimmer;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.aivox.common_ui.shimmer.ShimmerViewHelper;

/* JADX INFO: loaded from: classes.dex */
public class ShimmerTextView extends AppCompatTextView implements ShimmerViewBase {
    private ShimmerViewHelper shimmerViewHelper;

    public ShimmerTextView(Context context) {
        super(context);
        ShimmerViewHelper shimmerViewHelper = new ShimmerViewHelper(this, getPaint(), null);
        this.shimmerViewHelper = shimmerViewHelper;
        shimmerViewHelper.setPrimaryColor(getCurrentTextColor());
    }

    public ShimmerTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ShimmerViewHelper shimmerViewHelper = new ShimmerViewHelper(this, getPaint(), attributeSet);
        this.shimmerViewHelper = shimmerViewHelper;
        shimmerViewHelper.setPrimaryColor(getCurrentTextColor());
    }

    public ShimmerTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        ShimmerViewHelper shimmerViewHelper = new ShimmerViewHelper(this, getPaint(), attributeSet);
        this.shimmerViewHelper = shimmerViewHelper;
        shimmerViewHelper.setPrimaryColor(getCurrentTextColor());
    }

    @Override // com.aivox.common_ui.shimmer.ShimmerViewBase
    public float getGradientX() {
        return this.shimmerViewHelper.getGradientX();
    }

    @Override // com.aivox.common_ui.shimmer.ShimmerViewBase
    public void setGradientX(float f) {
        this.shimmerViewHelper.setGradientX(f);
    }

    @Override // com.aivox.common_ui.shimmer.ShimmerViewBase
    public boolean isShimmering() {
        return this.shimmerViewHelper.isShimmering();
    }

    @Override // com.aivox.common_ui.shimmer.ShimmerViewBase
    public void setShimmering(boolean z) {
        this.shimmerViewHelper.setShimmering(z);
    }

    @Override // com.aivox.common_ui.shimmer.ShimmerViewBase
    public boolean isSetUp() {
        return this.shimmerViewHelper.isSetUp();
    }

    @Override // com.aivox.common_ui.shimmer.ShimmerViewBase
    public void setAnimationSetupCallback(ShimmerViewHelper.AnimationSetupCallback animationSetupCallback) {
        this.shimmerViewHelper.setAnimationSetupCallback(animationSetupCallback);
    }

    @Override // com.aivox.common_ui.shimmer.ShimmerViewBase
    public int getPrimaryColor() {
        return this.shimmerViewHelper.getPrimaryColor();
    }

    @Override // com.aivox.common_ui.shimmer.ShimmerViewBase
    public void setPrimaryColor(int i) {
        this.shimmerViewHelper.setPrimaryColor(i);
    }

    @Override // com.aivox.common_ui.shimmer.ShimmerViewBase
    public int getReflectionColor() {
        return this.shimmerViewHelper.getReflectionColor();
    }

    @Override // com.aivox.common_ui.shimmer.ShimmerViewBase
    public void setReflectionColor(int i) {
        this.shimmerViewHelper.setReflectionColor(i);
    }

    @Override // android.widget.TextView
    public void setTextColor(int i) {
        super.setTextColor(i);
        ShimmerViewHelper shimmerViewHelper = this.shimmerViewHelper;
        if (shimmerViewHelper != null) {
            shimmerViewHelper.setPrimaryColor(getCurrentTextColor());
        }
    }

    @Override // android.widget.TextView
    public void setTextColor(ColorStateList colorStateList) {
        super.setTextColor(colorStateList);
        ShimmerViewHelper shimmerViewHelper = this.shimmerViewHelper;
        if (shimmerViewHelper != null) {
            shimmerViewHelper.setPrimaryColor(getCurrentTextColor());
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        ShimmerViewHelper shimmerViewHelper = this.shimmerViewHelper;
        if (shimmerViewHelper != null) {
            shimmerViewHelper.onSizeChanged();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        ShimmerViewHelper shimmerViewHelper = this.shimmerViewHelper;
        if (shimmerViewHelper != null) {
            shimmerViewHelper.onDraw();
        }
        super.onDraw(canvas);
    }
}
