package com.lxj.xpopup.animator;

import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.lxj.xpopup.enums.PopupAnimation;

/* JADX INFO: loaded from: classes3.dex */
public class TranslateAnimator extends PopupAnimator {
    private float endTranslationX;
    private float endTranslationY;
    private float startTranslationX;
    private float startTranslationY;

    public TranslateAnimator(View view2, int i, PopupAnimation popupAnimation) {
        super(view2, i, popupAnimation);
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void initAnimator() {
        this.endTranslationX = this.targetView.getTranslationX();
        this.endTranslationY = this.targetView.getTranslationY();
        applyTranslation();
        this.startTranslationX = this.targetView.getTranslationX();
        this.startTranslationY = this.targetView.getTranslationY();
    }

    /* JADX INFO: renamed from: com.lxj.xpopup.animator.TranslateAnimator$1 */
    static /* synthetic */ class C22281 {
        static final /* synthetic */ int[] $SwitchMap$com$lxj$xpopup$enums$PopupAnimation;

        static {
            int[] iArr = new int[PopupAnimation.values().length];
            $SwitchMap$com$lxj$xpopup$enums$PopupAnimation = iArr;
            try {
                iArr[PopupAnimation.TranslateFromLeft.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromTop.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromRight.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromBottom.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private void applyTranslation() {
        int i = C22281.$SwitchMap$com$lxj$xpopup$enums$PopupAnimation[this.popupAnimation.ordinal()];
        if (i == 1) {
            this.targetView.setTranslationX(-this.targetView.getRight());
            return;
        }
        if (i == 2) {
            this.targetView.setTranslationY(-this.targetView.getBottom());
        } else if (i == 3) {
            this.targetView.setTranslationX(((View) this.targetView.getParent()).getMeasuredWidth() - this.targetView.getLeft());
        } else {
            if (i != 4) {
                return;
            }
            this.targetView.setTranslationY(((View) this.targetView.getParent()).getMeasuredHeight() - this.targetView.getTop());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0025  */
    @Override // com.lxj.xpopup.animator.PopupAnimator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void animateShow() {
        /*
            r3 = this;
            int[] r0 = com.lxj.xpopup.animator.TranslateAnimator.C22281.$SwitchMap$com$lxj$xpopup$enums$PopupAnimation
            com.lxj.xpopup.enums.PopupAnimation r1 = r3.popupAnimation
            int r1 = r1.ordinal()
            r0 = r0[r1]
            r1 = 1
            if (r0 == r1) goto L25
            r1 = 2
            if (r0 == r1) goto L18
            r1 = 3
            if (r0 == r1) goto L25
            r1 = 4
            if (r0 == r1) goto L18
            r0 = 0
            goto L31
        L18:
            android.view.View r0 = r3.targetView
            android.view.ViewPropertyAnimator r0 = r0.animate()
            float r1 = r3.endTranslationY
            android.view.ViewPropertyAnimator r0 = r0.translationY(r1)
            goto L31
        L25:
            android.view.View r0 = r3.targetView
            android.view.ViewPropertyAnimator r0 = r0.animate()
            float r1 = r3.endTranslationX
            android.view.ViewPropertyAnimator r0 = r0.translationX(r1)
        L31:
            if (r0 == 0) goto L4a
            androidx.interpolator.view.animation.FastOutSlowInInterpolator r1 = new androidx.interpolator.view.animation.FastOutSlowInInterpolator
            r1.<init>()
            android.view.ViewPropertyAnimator r0 = r0.setInterpolator(r1)
            int r1 = r3.animationDuration
            long r1 = (long) r1
            android.view.ViewPropertyAnimator r0 = r0.setDuration(r1)
            android.view.ViewPropertyAnimator r0 = r0.withLayer()
            r0.start()
        L4a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lxj.xpopup.animator.TranslateAnimator.animateShow():void");
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateDismiss() {
        ViewPropertyAnimator viewPropertyAnimatorTranslationX;
        if (this.animating) {
            return;
        }
        int i = C22281.$SwitchMap$com$lxj$xpopup$enums$PopupAnimation[this.popupAnimation.ordinal()];
        if (i == 1) {
            this.startTranslationX = -this.targetView.getRight();
            viewPropertyAnimatorTranslationX = this.targetView.animate().translationX(this.startTranslationX);
        } else if (i == 2) {
            this.startTranslationY = -this.targetView.getBottom();
            viewPropertyAnimatorTranslationX = this.targetView.animate().translationY(this.startTranslationY);
        } else if (i == 3) {
            this.startTranslationX = ((View) this.targetView.getParent()).getMeasuredWidth() - this.targetView.getLeft();
            viewPropertyAnimatorTranslationX = this.targetView.animate().translationX(this.startTranslationX);
        } else if (i != 4) {
            viewPropertyAnimatorTranslationX = null;
        } else {
            this.startTranslationY = ((View) this.targetView.getParent()).getMeasuredHeight() - this.targetView.getTop();
            viewPropertyAnimatorTranslationX = this.targetView.animate().translationY(this.startTranslationY);
        }
        if (viewPropertyAnimatorTranslationX != null) {
            observerAnimator(viewPropertyAnimatorTranslationX.setInterpolator(new FastOutSlowInInterpolator()).setDuration((long) (((double) this.animationDuration) * 0.8d)).withLayer()).start();
        }
    }
}
