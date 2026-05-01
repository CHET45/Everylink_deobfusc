package com.lxj.xpopup.animator;

import android.view.View;

/* JADX INFO: loaded from: classes3.dex */
public class EmptyAnimator extends PopupAnimator {
    public EmptyAnimator(View view2, int i) {
        super(view2, i);
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void initAnimator() {
        this.targetView.setAlpha(0.0f);
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateShow() {
        this.targetView.animate().alpha(1.0f).setDuration(this.animationDuration).withLayer().start();
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateDismiss() {
        if (this.animating) {
            return;
        }
        observerAnimator(this.targetView.animate().alpha(0.0f).setDuration(this.animationDuration).withLayer()).start();
    }
}
