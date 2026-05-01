package com.chad.library.adapter.base.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/* JADX INFO: loaded from: classes3.dex */
public class SlideInBottomAnimation implements BaseAnimation {
    @Override // com.chad.library.adapter.base.animation.BaseAnimation
    public Animator[] getAnimators(View view2) {
        return new Animator[]{ObjectAnimator.ofFloat(view2, "translationY", view2.getMeasuredHeight(), 0.0f)};
    }
}
