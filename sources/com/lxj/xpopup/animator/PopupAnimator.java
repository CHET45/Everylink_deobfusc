package com.lxj.xpopup.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import com.lxj.xpopup.enums.PopupAnimation;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PopupAnimator {
    protected boolean animating;
    public int animationDuration;
    public PopupAnimation popupAnimation;
    public View targetView;

    public abstract void animateDismiss();

    public abstract void animateShow();

    public abstract void initAnimator();

    public PopupAnimator() {
        this.animating = false;
        this.animationDuration = 0;
    }

    public PopupAnimator(View view2, int i) {
        this(view2, i, null);
    }

    public PopupAnimator(View view2, int i, PopupAnimation popupAnimation) {
        this.animating = false;
        this.targetView = view2;
        this.animationDuration = i;
        this.popupAnimation = popupAnimation;
    }

    public int getDuration() {
        return this.animationDuration;
    }

    protected ValueAnimator observerAnimator(ValueAnimator valueAnimator) {
        valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.lxj.xpopup.animator.PopupAnimator.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                PopupAnimator.this.animating = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                PopupAnimator.this.animating = false;
            }
        });
        return valueAnimator;
    }

    protected ViewPropertyAnimator observerAnimator(ViewPropertyAnimator viewPropertyAnimator) {
        viewPropertyAnimator.setListener(new AnimatorListenerAdapter() { // from class: com.lxj.xpopup.animator.PopupAnimator.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                PopupAnimator.this.animating = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                PopupAnimator.this.animating = false;
            }
        });
        return viewPropertyAnimator;
    }
}
