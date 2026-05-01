package com.aivox.common_ui.pulltorefresh.internal;

import android.content.Context;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.pulltorefresh.PullToRefreshBase;

/* JADX INFO: loaded from: classes.dex */
public class IndicatorLayout extends FrameLayout implements Animation.AnimationListener {
    static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;
    private ImageView mArrowImageView;
    private Animation mInAnim;
    private Animation mOutAnim;
    private final Animation mResetRotateAnimation;
    private final Animation mRotateAnimation;

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationRepeat(Animation animation) {
    }

    public IndicatorLayout(Context context, PullToRefreshBase.Mode mode) {
        int i;
        int i2;
        super(context);
        this.mArrowImageView = new ImageView(context);
        this.mArrowImageView.setImageDrawable(getResources().getDrawable(C1034R.drawable.pull_icon_big));
        int dimensionPixelSize = getResources().getDimensionPixelSize(C1034R.dimen.indicator_internal_padding);
        this.mArrowImageView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        addView(this.mArrowImageView);
        if (C10611.f283x5281aad0[mode.ordinal()] == 1) {
            i = C1034R.anim.slide_in_from_bottom;
            int i3 = C1034R.anim.slide_out_to_bottom;
            setBackgroundResource(C1034R.drawable.indicator_bg_bottom);
            this.mArrowImageView.setScaleType(ImageView.ScaleType.MATRIX);
            Matrix matrix = new Matrix();
            matrix.setRotate(180.0f, r0.getIntrinsicWidth() / 2.0f, r0.getIntrinsicHeight() / 2.0f);
            this.mArrowImageView.setImageMatrix(matrix);
            i2 = i3;
        } else {
            i = C1034R.anim.slide_in_from_top;
            i2 = C1034R.anim.slide_out_to_top;
            setBackgroundResource(C1034R.drawable.indicator_bg_top);
        }
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(context, i);
        this.mInAnim = animationLoadAnimation;
        animationLoadAnimation.setAnimationListener(this);
        Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(context, i2);
        this.mOutAnim = animationLoadAnimation2;
        animationLoadAnimation2.setAnimationListener(this);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, -180.0f, 1, 0.5f, 1, 0.5f);
        this.mRotateAnimation = rotateAnimation;
        rotateAnimation.setInterpolator(linearInterpolator);
        rotateAnimation.setDuration(150L);
        rotateAnimation.setFillAfter(true);
        RotateAnimation rotateAnimation2 = new RotateAnimation(-180.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        this.mResetRotateAnimation = rotateAnimation2;
        rotateAnimation2.setInterpolator(linearInterpolator);
        rotateAnimation2.setDuration(150L);
        rotateAnimation2.setFillAfter(true);
    }

    /* JADX INFO: renamed from: com.aivox.common_ui.pulltorefresh.internal.IndicatorLayout$1 */
    static /* synthetic */ class C10611 {

        /* JADX INFO: renamed from: $SwitchMap$com$aivox$common_ui$pulltorefresh$PullToRefreshBase$Mode */
        static final /* synthetic */ int[] f283x5281aad0;

        static {
            int[] iArr = new int[PullToRefreshBase.Mode.values().length];
            f283x5281aad0 = iArr;
            try {
                iArr[PullToRefreshBase.Mode.PULL_FROM_END.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f283x5281aad0[PullToRefreshBase.Mode.PULL_FROM_START.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public final boolean isVisible() {
        Animation animation = getAnimation();
        return animation != null ? this.mInAnim == animation : getVisibility() == 0;
    }

    public void hide() {
        startAnimation(this.mOutAnim);
    }

    public void show() {
        this.mArrowImageView.clearAnimation();
        startAnimation(this.mInAnim);
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        if (animation == this.mOutAnim) {
            this.mArrowImageView.clearAnimation();
            setVisibility(8);
        } else if (animation == this.mInAnim) {
            setVisibility(0);
        }
        clearAnimation();
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationStart(Animation animation) {
        setVisibility(0);
    }

    public void releaseToRefresh() {
        this.mArrowImageView.startAnimation(this.mRotateAnimation);
    }

    public void pullToRefresh() {
        this.mArrowImageView.startAnimation(this.mResetRotateAnimation);
    }
}
