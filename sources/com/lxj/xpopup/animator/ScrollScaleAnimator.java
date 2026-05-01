package com.lxj.xpopup.animator;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.view.View;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.lxj.xpopup.enums.PopupAnimation;

/* JADX INFO: loaded from: classes3.dex */
public class ScrollScaleAnimator extends PopupAnimator {
    private IntEvaluator intEvaluator;
    public boolean isOnlyScaleX;
    private float startAlpha;
    private float startScale;
    private int startScrollX;
    private int startScrollY;

    public ScrollScaleAnimator(View view2, int i, PopupAnimation popupAnimation) {
        super(view2, i, popupAnimation);
        this.intEvaluator = new IntEvaluator();
        this.startAlpha = 0.0f;
        this.startScale = 0.0f;
        this.isOnlyScaleX = false;
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void initAnimator() {
        this.targetView.setAlpha(this.startAlpha);
        this.targetView.setScaleX(this.startScale);
        if (!this.isOnlyScaleX) {
            this.targetView.setScaleY(this.startScale);
        }
        this.targetView.post(new Runnable() { // from class: com.lxj.xpopup.animator.ScrollScaleAnimator.1
            @Override // java.lang.Runnable
            public void run() {
                ScrollScaleAnimator.this.applyPivot();
                ScrollScaleAnimator.this.targetView.scrollTo(ScrollScaleAnimator.this.startScrollX, ScrollScaleAnimator.this.startScrollY);
            }
        });
    }

    /* JADX INFO: renamed from: com.lxj.xpopup.animator.ScrollScaleAnimator$4 */
    static /* synthetic */ class C22244 {
        static final /* synthetic */ int[] $SwitchMap$com$lxj$xpopup$enums$PopupAnimation;

        static {
            int[] iArr = new int[PopupAnimation.values().length];
            $SwitchMap$com$lxj$xpopup$enums$PopupAnimation = iArr;
            try {
                iArr[PopupAnimation.ScrollAlphaFromLeft.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromLeftTop.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromTop.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromRightTop.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromRight.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromRightBottom.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromBottom.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromLeftBottom.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyPivot() {
        switch (C22244.$SwitchMap$com$lxj$xpopup$enums$PopupAnimation[this.popupAnimation.ordinal()]) {
            case 1:
                this.targetView.setPivotX(0.0f);
                this.targetView.setPivotY(this.targetView.getMeasuredHeight() / 2);
                this.startScrollX = this.targetView.getMeasuredWidth();
                this.startScrollY = 0;
                break;
            case 2:
                this.targetView.setPivotX(0.0f);
                this.targetView.setPivotY(0.0f);
                this.startScrollX = this.targetView.getMeasuredWidth();
                this.startScrollY = this.targetView.getMeasuredHeight();
                break;
            case 3:
                this.targetView.setPivotX(this.targetView.getMeasuredWidth() / 2);
                this.targetView.setPivotY(0.0f);
                this.startScrollY = this.targetView.getMeasuredHeight();
                break;
            case 4:
                this.targetView.setPivotX(this.targetView.getMeasuredWidth());
                this.targetView.setPivotY(0.0f);
                this.startScrollX = -this.targetView.getMeasuredWidth();
                this.startScrollY = this.targetView.getMeasuredHeight();
                break;
            case 5:
                this.targetView.setPivotX(this.targetView.getMeasuredWidth());
                this.targetView.setPivotY(this.targetView.getMeasuredHeight() / 2);
                this.startScrollX = -this.targetView.getMeasuredWidth();
                break;
            case 6:
                this.targetView.setPivotX(this.targetView.getMeasuredWidth());
                this.targetView.setPivotY(this.targetView.getMeasuredHeight());
                this.startScrollX = -this.targetView.getMeasuredWidth();
                this.startScrollY = -this.targetView.getMeasuredHeight();
                break;
            case 7:
                this.targetView.setPivotX(this.targetView.getMeasuredWidth() / 2);
                this.targetView.setPivotY(this.targetView.getMeasuredHeight());
                this.startScrollY = -this.targetView.getMeasuredHeight();
                break;
            case 8:
                this.targetView.setPivotX(0.0f);
                this.targetView.setPivotY(this.targetView.getMeasuredHeight());
                this.startScrollX = this.targetView.getMeasuredWidth();
                this.startScrollY = -this.targetView.getMeasuredHeight();
                break;
        }
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateShow() {
        this.targetView.post(new Runnable() { // from class: com.lxj.xpopup.animator.ScrollScaleAnimator.2
            @Override // java.lang.Runnable
            public void run() {
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lxj.xpopup.animator.ScrollScaleAnimator.2.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float animatedFraction = valueAnimator.getAnimatedFraction();
                        ScrollScaleAnimator.this.targetView.setAlpha(animatedFraction);
                        ScrollScaleAnimator.this.targetView.scrollTo(ScrollScaleAnimator.this.intEvaluator.evaluate(animatedFraction, Integer.valueOf(ScrollScaleAnimator.this.startScrollX), (Integer) 0).intValue(), ScrollScaleAnimator.this.intEvaluator.evaluate(animatedFraction, Integer.valueOf(ScrollScaleAnimator.this.startScrollY), (Integer) 0).intValue());
                        ScrollScaleAnimator.this.targetView.setScaleX(animatedFraction);
                        if (ScrollScaleAnimator.this.isOnlyScaleX) {
                            return;
                        }
                        ScrollScaleAnimator.this.targetView.setScaleY(animatedFraction);
                    }
                });
                valueAnimatorOfFloat.setDuration(ScrollScaleAnimator.this.animationDuration).setInterpolator(new FastOutSlowInInterpolator());
                valueAnimatorOfFloat.start();
            }
        });
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateDismiss() {
        if (this.animating) {
            return;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        observerAnimator(valueAnimatorOfFloat);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lxj.xpopup.animator.ScrollScaleAnimator.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                float f = 1.0f - animatedFraction;
                ScrollScaleAnimator.this.targetView.setAlpha(f);
                ScrollScaleAnimator.this.targetView.scrollTo(ScrollScaleAnimator.this.intEvaluator.evaluate(animatedFraction, (Integer) 0, Integer.valueOf(ScrollScaleAnimator.this.startScrollX)).intValue(), ScrollScaleAnimator.this.intEvaluator.evaluate(animatedFraction, (Integer) 0, Integer.valueOf(ScrollScaleAnimator.this.startScrollY)).intValue());
                ScrollScaleAnimator.this.targetView.setScaleX(f);
                if (ScrollScaleAnimator.this.isOnlyScaleX) {
                    return;
                }
                ScrollScaleAnimator.this.targetView.setScaleY(f);
            }
        });
        valueAnimatorOfFloat.setDuration(this.animationDuration).setInterpolator(new FastOutSlowInInterpolator());
        valueAnimatorOfFloat.start();
    }
}
