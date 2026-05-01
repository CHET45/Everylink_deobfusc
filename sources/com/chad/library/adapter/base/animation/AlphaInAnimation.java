package com.chad.library.adapter.base.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/* JADX INFO: loaded from: classes3.dex */
public class AlphaInAnimation implements BaseAnimation {
    private static final float DEFAULT_ALPHA_FROM = 0.0f;
    private final float mFrom;

    public AlphaInAnimation() {
        this(0.0f);
    }

    public AlphaInAnimation(float f) {
        this.mFrom = f;
    }

    @Override // com.chad.library.adapter.base.animation.BaseAnimation
    public Animator[] getAnimators(View view2) {
        return new Animator[]{ObjectAnimator.ofFloat(view2, "alpha", this.mFrom, 1.0f)};
    }
}
