package com.chad.library.adapter.base.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/* JADX INFO: loaded from: classes3.dex */
public class ScaleInAnimation implements BaseAnimation {
    private static final float DEFAULT_SCALE_FROM = 0.5f;
    private final float mFrom;

    public ScaleInAnimation() {
        this(0.5f);
    }

    public ScaleInAnimation(float f) {
        this.mFrom = f;
    }

    @Override // com.chad.library.adapter.base.animation.BaseAnimation
    public Animator[] getAnimators(View view2) {
        return new ObjectAnimator[]{ObjectAnimator.ofFloat(view2, "scaleX", this.mFrom, 1.0f), ObjectAnimator.ofFloat(view2, "scaleY", this.mFrom, 1.0f)};
    }
}
