package com.aivox.common_ui.pulltorefresh.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.pulltorefresh.PullToRefreshBase;

/* JADX INFO: loaded from: classes.dex */
public class FlipLoadingLayout extends LoadingLayout {
    static final int FLIP_ANIMATION_DURATION = 150;
    private final Animation mResetRotateAnimation;
    private final Animation mRotateAnimation;

    @Override // com.aivox.common_ui.pulltorefresh.internal.LoadingLayout
    protected void onPullImpl(float f) {
    }

    public FlipLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation orientation, TypedArray typedArray) {
        super(context, mode, orientation, typedArray);
        float f = mode == PullToRefreshBase.Mode.PULL_FROM_START ? -180 : 180;
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, f, 1, 0.5f, 1, 0.5f);
        this.mRotateAnimation = rotateAnimation;
        rotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        rotateAnimation.setDuration(150L);
        rotateAnimation.setFillAfter(true);
        RotateAnimation rotateAnimation2 = new RotateAnimation(f, 0.0f, 1, 0.5f, 1, 0.5f);
        this.mResetRotateAnimation = rotateAnimation2;
        rotateAnimation2.setInterpolator(ANIMATION_INTERPOLATOR);
        rotateAnimation2.setDuration(150L);
        rotateAnimation2.setFillAfter(true);
    }

    @Override // com.aivox.common_ui.pulltorefresh.internal.LoadingLayout
    protected void onLoadingDrawableSet(Drawable drawable) {
        if (drawable != null) {
            int intrinsicHeight = drawable.getIntrinsicHeight();
            int intrinsicWidth = drawable.getIntrinsicWidth();
            ViewGroup.LayoutParams layoutParams = this.mHeaderImage.getLayoutParams();
            int iMax = Math.max(intrinsicHeight, intrinsicWidth);
            layoutParams.height = iMax;
            layoutParams.width = iMax;
            this.mHeaderImage.requestLayout();
            this.mHeaderImage.setScaleType(ImageView.ScaleType.MATRIX);
            Matrix matrix = new Matrix();
            matrix.postTranslate((layoutParams.width - intrinsicWidth) / 2.0f, (layoutParams.height - intrinsicHeight) / 2.0f);
            matrix.postRotate(getDrawableRotationAngle(), layoutParams.width / 2.0f, layoutParams.height / 2.0f);
            this.mHeaderImage.setImageMatrix(matrix);
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.internal.LoadingLayout
    protected void pullToRefreshImpl() {
        if (this.mRotateAnimation == this.mHeaderImage.getAnimation()) {
            this.mHeaderImage.startAnimation(this.mResetRotateAnimation);
        }
    }

    @Override // com.aivox.common_ui.pulltorefresh.internal.LoadingLayout
    protected void refreshingImpl() {
        this.mHeaderImage.clearAnimation();
        this.mHeaderImage.setVisibility(4);
        this.mHeaderProgress.setVisibility(0);
    }

    @Override // com.aivox.common_ui.pulltorefresh.internal.LoadingLayout
    protected void releaseToRefreshImpl() {
        this.mHeaderImage.startAnimation(this.mRotateAnimation);
    }

    @Override // com.aivox.common_ui.pulltorefresh.internal.LoadingLayout
    protected void resetImpl() {
        this.mHeaderImage.clearAnimation();
        this.mHeaderProgress.setVisibility(8);
        this.mHeaderImage.setVisibility(0);
    }

    @Override // com.aivox.common_ui.pulltorefresh.internal.LoadingLayout
    protected int getDefaultDrawableResId() {
        return C1034R.drawable.pull_icon_big;
    }

    /* JADX INFO: renamed from: com.aivox.common_ui.pulltorefresh.internal.FlipLoadingLayout$1 */
    static /* synthetic */ class C10601 {

        /* JADX INFO: renamed from: $SwitchMap$com$aivox$common_ui$pulltorefresh$PullToRefreshBase$Mode */
        static final /* synthetic */ int[] f282x5281aad0;

        static {
            int[] iArr = new int[PullToRefreshBase.Mode.values().length];
            f282x5281aad0 = iArr;
            try {
                iArr[PullToRefreshBase.Mode.PULL_FROM_END.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f282x5281aad0[PullToRefreshBase.Mode.PULL_FROM_START.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private float getDrawableRotationAngle() {
        int i = C10601.f282x5281aad0[this.mMode.ordinal()];
        return i != 1 ? (i == 2 && this.mScrollDirection == PullToRefreshBase.Orientation.HORIZONTAL) ? 270.0f : 0.0f : this.mScrollDirection == PullToRefreshBase.Orientation.HORIZONTAL ? 90.0f : 180.0f;
    }
}
