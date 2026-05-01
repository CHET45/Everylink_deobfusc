package com.aivox.common_ui.pulltorefresh.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.pulltorefresh.PullToRefreshBase;

/* JADX INFO: loaded from: classes.dex */
public class RotateLoadingLayout extends LoadingLayout {
    static final int ROTATION_ANIMATION_DURATION = 1200;
    private final Matrix mHeaderImageMatrix;
    private final Animation mRotateAnimation;
    private final boolean mRotateDrawableWhilePulling;
    private float mRotationPivotX;
    private float mRotationPivotY;

    @Override // com.aivox.common_ui.pulltorefresh.internal.LoadingLayout
    protected void pullToRefreshImpl() {
    }

    @Override // com.aivox.common_ui.pulltorefresh.internal.LoadingLayout
    protected void releaseToRefreshImpl() {
    }

    public RotateLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation orientation, TypedArray typedArray) {
        super(context, mode, orientation, typedArray);
        this.mRotateDrawableWhilePulling = typedArray.getBoolean(C1034R.styleable.PullToRefresh_ptrRotateDrawableWhilePulling, true);
        this.mHeaderImage.setScaleType(ImageView.ScaleType.MATRIX);
        Matrix matrix = new Matrix();
        this.mHeaderImageMatrix = matrix;
        this.mHeaderImage.setImageMatrix(matrix);
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 720.0f, 1, 0.5f, 1, 0.5f);
        this.mRotateAnimation = rotateAnimation;
        rotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        rotateAnimation.setDuration(1200L);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setRepeatMode(1);
    }

    @Override // com.aivox.common_ui.pulltorefresh.internal.LoadingLayout
    public void onLoadingDrawableSet(Drawable drawable) {
        if (drawable != null) {
            this.mRotationPivotX = Math.round(drawable.getIntrinsicWidth() / 2.0f);
            this.mRotationPivotY = Math.round(drawable.getIntrinsicHeight() / 2.0f);
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.internal.LoadingLayout
    protected void onPullImpl(float f) {
        this.mHeaderImageMatrix.setRotate(this.mRotateDrawableWhilePulling ? f * 90.0f : Math.max(0.0f, Math.min(180.0f, (f * 360.0f) - 180.0f)), this.mRotationPivotX, this.mRotationPivotY);
        this.mHeaderImage.setImageMatrix(this.mHeaderImageMatrix);
    }

    @Override // com.aivox.common_ui.pulltorefresh.internal.LoadingLayout
    protected void refreshingImpl() {
        this.mHeaderImage.startAnimation(this.mRotateAnimation);
    }

    @Override // com.aivox.common_ui.pulltorefresh.internal.LoadingLayout
    protected void resetImpl() {
        this.mHeaderImage.clearAnimation();
        resetImageRotation();
    }

    private void resetImageRotation() {
        Matrix matrix = this.mHeaderImageMatrix;
        if (matrix != null) {
            matrix.reset();
            this.mHeaderImage.setImageMatrix(this.mHeaderImageMatrix);
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.internal.LoadingLayout
    protected int getDefaultDrawableResId() {
        return C1034R.drawable.default_ptr_rotate;
    }
}
