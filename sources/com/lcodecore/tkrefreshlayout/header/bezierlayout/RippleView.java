package com.lcodecore.tkrefreshlayout.header.bezierlayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: loaded from: classes3.dex */
public class RippleView extends View {
    private OnRippleEndListener listener;
    private Paint mPaint;

    /* JADX INFO: renamed from: r */
    private int f810r;

    /* JADX INFO: renamed from: va */
    ValueAnimator f811va;

    public interface OnRippleEndListener {
        void onRippleEnd();
    }

    public int getR() {
        return this.f810r;
    }

    public void setR(int i) {
        this.f810r = i;
    }

    public RippleView(Context context) {
        this(context, null, 0);
    }

    public RippleView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RippleView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setColor(-1);
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    public void setRippleColor(int i) {
        Paint paint = this.mPaint;
        if (paint != null) {
            paint.setColor(i);
        }
    }

    public void startReveal() {
        setVisibility(0);
        if (this.f811va == null) {
            int iSqrt = (int) Math.sqrt(Math.pow(getHeight(), 2.0d) + Math.pow(getWidth(), 2.0d));
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, iSqrt / 2);
            this.f811va = valueAnimatorOfInt;
            valueAnimatorOfInt.setDuration(iSqrt);
            this.f811va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lcodecore.tkrefreshlayout.header.bezierlayout.RippleView.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    RippleView.this.f810r = ((Integer) valueAnimator.getAnimatedValue()).intValue() * 2;
                    RippleView.this.invalidate();
                }
            });
            this.f811va.addListener(new AnimatorListenerAdapter() { // from class: com.lcodecore.tkrefreshlayout.header.bezierlayout.RippleView.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (RippleView.this.listener != null) {
                        RippleView.this.listener.onRippleEnd();
                    }
                }
            });
        }
        this.f811va.start();
    }

    public void stopAnim() {
        ValueAnimator valueAnimator = this.f811va;
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            return;
        }
        this.f811va.cancel();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, this.f810r, this.mPaint);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ValueAnimator valueAnimator = this.f811va;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    public void setRippleEndListener(OnRippleEndListener onRippleEndListener) {
        this.listener = onRippleEndListener;
    }
}
