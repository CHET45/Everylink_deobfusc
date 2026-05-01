package com.chad.library.adapter.base.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/* JADX INFO: loaded from: classes3.dex */
public class SlideInRightAnimation implements BaseAnimation {
    @Override // com.chad.library.adapter.base.animation.BaseAnimation
    public Animator[] getAnimators(View view2) {
        return new Animator[]{ObjectAnimator.ofFloat(view2, "translationX", view2.getRootView().getWidth(), 0.0f)};
    }
}
