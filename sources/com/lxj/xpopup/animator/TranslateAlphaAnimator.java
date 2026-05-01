package com.lxj.xpopup.animator;

import android.view.View;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.lxj.xpopup.enums.PopupAnimation;

/* JADX INFO: loaded from: classes3.dex */
public class TranslateAlphaAnimator extends PopupAnimator {
    private float defTranslationX;
    private float defTranslationY;
    private float startTranslationX;
    private float startTranslationY;

    public TranslateAlphaAnimator(View view2, int i, PopupAnimation popupAnimation) {
        super(view2, i, popupAnimation);
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void initAnimator() {
        this.defTranslationX = this.targetView.getTranslationX();
        this.defTranslationY = this.targetView.getTranslationY();
        this.targetView.setAlpha(0.0f);
        applyTranslation();
        this.startTranslationX = this.targetView.getTranslationX();
        this.startTranslationY = this.targetView.getTranslationY();
    }

    /* JADX INFO: renamed from: com.lxj.xpopup.animator.TranslateAlphaAnimator$1 */
    static /* synthetic */ class C22271 {
        static final /* synthetic */ int[] $SwitchMap$com$lxj$xpopup$enums$PopupAnimation;

        static {
            int[] iArr = new int[PopupAnimation.values().length];
            $SwitchMap$com$lxj$xpopup$enums$PopupAnimation = iArr;
            try {
                iArr[PopupAnimation.TranslateAlphaFromLeft.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateAlphaFromTop.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateAlphaFromRight.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateAlphaFromBottom.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private void applyTranslation() {
        int i = C22271.$SwitchMap$com$lxj$xpopup$enums$PopupAnimation[this.popupAnimation.ordinal()];
        if (i == 1) {
            this.targetView.setTranslationX(-this.targetView.getMeasuredWidth());
            return;
        }
        if (i == 2) {
            this.targetView.setTranslationY(-this.targetView.getMeasuredHeight());
        } else if (i == 3) {
            this.targetView.setTranslationX(this.targetView.getMeasuredWidth());
        } else {
            if (i != 4) {
                return;
            }
            this.targetView.setTranslationY(this.targetView.getMeasuredHeight());
        }
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateShow() {
        this.targetView.animate().translationX(this.defTranslationX).translationY(this.defTranslationY).alpha(1.0f).setInterpolator(new FastOutSlowInInterpolator()).setDuration(this.animationDuration).withLayer().start();
    }

    @Override // com.lxj.xpopup.animator.PopupAnimator
    public void animateDismiss() {
        if (this.animating) {
            return;
        }
        observerAnimator(this.targetView.animate().translationX(this.startTranslationX).translationY(this.startTranslationY).alpha(0.0f).setInterpolator(new FastOutSlowInInterpolator()).setDuration(this.animationDuration).withLayer()).start();
    }
}
