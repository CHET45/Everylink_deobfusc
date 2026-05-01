package com.lcodecore.tkrefreshlayout.header.bezierlayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import com.lcodecore.tkrefreshlayout.C2015R;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;
import com.lcodecore.tkrefreshlayout.header.bezierlayout.RippleView;

/* JADX INFO: loaded from: classes3.dex */
public class BezierLayout extends FrameLayout implements IHeaderView {
    private ValueAnimator circleAnimator;
    View headView;

    /* JADX INFO: renamed from: r1 */
    RoundDotView f808r1;

    /* JADX INFO: renamed from: r2 */
    RoundProgressView f809r2;
    RippleView rippleView;
    private ValueAnimator waveAnimator;
    WaveView waveView;

    @Override // com.lcodecore.tkrefreshlayout.IHeaderView
    public View getView() {
        return this;
    }

    public BezierLayout(Context context) {
        this(context, null);
    }

    public BezierLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BezierLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        View viewInflate = LayoutInflater.from(getContext()).inflate(C2015R.layout.view_bezier, (ViewGroup) null);
        this.headView = viewInflate;
        this.waveView = (WaveView) viewInflate.findViewById(C2015R.id.draweeView);
        this.rippleView = (RippleView) this.headView.findViewById(C2015R.id.ripple);
        this.f808r1 = (RoundDotView) this.headView.findViewById(C2015R.id.round1);
        RoundProgressView roundProgressView = (RoundProgressView) this.headView.findViewById(C2015R.id.round2);
        this.f809r2 = roundProgressView;
        roundProgressView.setVisibility(8);
        addView(this.headView);
    }

    public void setWaveColor(int i) {
        this.waveView.setWaveColor(i);
    }

    public void setRippleColor(int i) {
        this.rippleView.setRippleColor(i);
    }

    public float limitValue(float f, float f2) {
        float fMin = Math.min(f, f2);
        float fMax = Math.max(f, f2);
        if (0.0f > fMin) {
            fMin = 0.0f;
        }
        return fMin < fMax ? fMin : fMax;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ValueAnimator valueAnimator = this.waveAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator valueAnimator2 = this.circleAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
        }
    }

    @Override // com.lcodecore.tkrefreshlayout.IHeaderView
    public void onPullingDown(float f, float f2, float f3) {
        if (this.rippleView.getVisibility() == 0) {
            this.rippleView.setVisibility(8);
        }
        this.waveView.setHeadHeight((int) (f3 * limitValue(1.0f, f)));
        this.waveView.setWaveHeight((int) (f2 * Math.max(0.0f, f - 1.0f)));
        this.waveView.invalidate();
        this.f808r1.setCir_x((int) (limitValue(1.0f, f) * 30.0f));
        this.f808r1.setVisibility(0);
        this.f808r1.invalidate();
        this.f809r2.setVisibility(8);
        this.f809r2.animate().scaleX(0.1f);
        this.f809r2.animate().scaleY(0.1f);
    }

    @Override // com.lcodecore.tkrefreshlayout.IHeaderView
    public void onPullReleasing(float f, float f2, float f3) {
        this.waveView.setHeadHeight((int) (f3 * limitValue(1.0f, f)));
        this.waveView.setWaveHeight((int) (f2 * Math.max(0.0f, f - 1.0f)));
        this.waveView.invalidate();
        this.f808r1.setCir_x((int) (limitValue(1.0f, f) * 30.0f));
        this.f808r1.invalidate();
    }

    @Override // com.lcodecore.tkrefreshlayout.IHeaderView
    public void startAnim(float f, float f2) {
        this.waveView.setHeadHeight((int) f2);
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.waveView.getWaveHeight(), 0, -300, 0, -100, 0);
        this.waveAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                BezierLayout.this.waveView.setWaveHeight(((Integer) valueAnimator.getAnimatedValue()).intValue() / 2);
                BezierLayout.this.waveView.invalidate();
            }
        });
        this.waveAnimator.setInterpolator(new DecelerateInterpolator());
        this.waveAnimator.setDuration(800L);
        this.waveAnimator.start();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.circleAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                BezierLayout.this.f808r1.setVisibility(8);
                BezierLayout.this.f809r2.setVisibility(0);
                BezierLayout.this.f809r2.animate().scaleX(1.0f);
                BezierLayout.this.f809r2.animate().scaleY(1.0f);
                BezierLayout.this.f809r2.postDelayed(new Runnable() { // from class: com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        BezierLayout.this.f809r2.startAnim();
                    }
                }, 200L);
            }
        });
        this.circleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                BezierLayout.this.f808r1.setCir_x((int) ((-((Float) valueAnimator.getAnimatedValue()).floatValue()) * 40.0f));
                BezierLayout.this.f808r1.invalidate();
            }
        });
        this.circleAnimator.setInterpolator(new DecelerateInterpolator());
        this.circleAnimator.setDuration(300L);
        this.circleAnimator.start();
    }

    @Override // com.lcodecore.tkrefreshlayout.IHeaderView
    public void onFinish(final OnAnimEndListener onAnimEndListener) {
        this.f809r2.stopAnim();
        this.f809r2.animate().scaleX(0.0f);
        this.f809r2.animate().scaleY(0.0f);
        this.rippleView.setRippleEndListener(new RippleView.OnRippleEndListener() { // from class: com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout.4
            @Override // com.lcodecore.tkrefreshlayout.header.bezierlayout.RippleView.OnRippleEndListener
            public void onRippleEnd() {
                onAnimEndListener.onAnimEnd();
            }
        });
        this.rippleView.startReveal();
    }

    @Override // com.lcodecore.tkrefreshlayout.IHeaderView
    public void reset() {
        ValueAnimator valueAnimator = this.waveAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.waveAnimator.cancel();
        }
        this.waveView.setWaveHeight(0);
        ValueAnimator valueAnimator2 = this.circleAnimator;
        if (valueAnimator2 != null && valueAnimator2.isRunning()) {
            this.circleAnimator.cancel();
        }
        this.f808r1.setVisibility(0);
        this.f809r2.stopAnim();
        this.f809r2.setScaleX(0.0f);
        this.f809r2.setScaleY(0.0f);
        this.f809r2.setVisibility(8);
        this.rippleView.stopAnim();
        this.rippleView.setVisibility(8);
    }
}
