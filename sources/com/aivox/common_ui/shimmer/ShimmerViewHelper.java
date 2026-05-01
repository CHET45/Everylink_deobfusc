package com.aivox.common_ui.shimmer;

import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public class ShimmerViewHelper {
    private static final int DEFAULT_REFLECTION_COLOR = -1;
    private AnimationSetupCallback callback;
    private float gradientX;
    private boolean isSetUp;
    private boolean isShimmering;
    private LinearGradient linearGradient;
    private Matrix linearGradientMatrix;
    private Paint paint;
    private int primaryColor;
    private int reflectionColor;

    /* JADX INFO: renamed from: view, reason: collision with root package name */
    private View f2331view;

    public interface AnimationSetupCallback {
        void onSetupAnimation(View view2);
    }

    public ShimmerViewHelper(View view2, Paint paint, AttributeSet attributeSet) {
        this.f2331view = view2;
        this.paint = paint;
        init(attributeSet);
    }

    public float getGradientX() {
        return this.gradientX;
    }

    public void setGradientX(float f) {
        this.gradientX = f;
        this.f2331view.invalidate();
    }

    public boolean isShimmering() {
        return this.isShimmering;
    }

    public void setShimmering(boolean z) {
        this.isShimmering = z;
    }

    public boolean isSetUp() {
        return this.isSetUp;
    }

    public void setAnimationSetupCallback(AnimationSetupCallback animationSetupCallback) {
        this.callback = animationSetupCallback;
    }

    public int getPrimaryColor() {
        return this.primaryColor;
    }

    public void setPrimaryColor(int i) {
        this.primaryColor = i;
        if (this.isSetUp) {
            resetLinearGradient();
        }
    }

    public int getReflectionColor() {
        return this.reflectionColor;
    }

    public void setReflectionColor(int i) {
        this.reflectionColor = i;
        if (this.isSetUp) {
            resetLinearGradient();
        }
    }

    private void init(AttributeSet attributeSet) {
        this.reflectionColor = -1;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = this.f2331view.getContext().obtainStyledAttributes(attributeSet, C1034R.styleable.ShimmerView, 0, 0);
            try {
                if (typedArrayObtainStyledAttributes != null) {
                    try {
                        this.reflectionColor = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.ShimmerView_reflectionColor, -1);
                    } catch (Exception e) {
                        BaseAppUtils.printErrorMsg(e);
                        Log.e("ShimmerTextView", "Error while creating the view:", e);
                    }
                }
            } finally {
                typedArrayObtainStyledAttributes.recycle();
            }
        }
        this.linearGradientMatrix = new Matrix();
    }

    private void resetLinearGradient() {
        float f = -this.f2331view.getWidth();
        int i = this.primaryColor;
        LinearGradient linearGradient = new LinearGradient(f, 0.0f, 0.0f, 0.0f, new int[]{i, this.reflectionColor, i}, new float[]{0.0f, 0.5f, 1.0f}, Shader.TileMode.CLAMP);
        this.linearGradient = linearGradient;
        this.paint.setShader(linearGradient);
    }

    protected void onSizeChanged() {
        resetLinearGradient();
        if (this.isSetUp) {
            return;
        }
        this.isSetUp = true;
        AnimationSetupCallback animationSetupCallback = this.callback;
        if (animationSetupCallback != null) {
            animationSetupCallback.onSetupAnimation(this.f2331view);
        }
    }

    public void onDraw() {
        if (this.isShimmering) {
            if (this.paint.getShader() == null) {
                this.paint.setShader(this.linearGradient);
            }
            this.linearGradientMatrix.setTranslate(this.gradientX * 2.0f, 0.0f);
            this.linearGradient.setLocalMatrix(this.linearGradientMatrix);
            return;
        }
        this.paint.setShader(null);
    }
}
