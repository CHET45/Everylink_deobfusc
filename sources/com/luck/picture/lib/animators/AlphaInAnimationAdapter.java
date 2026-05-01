package com.luck.picture.lib.animators;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes3.dex */
public class AlphaInAnimationAdapter extends BaseAnimationAdapter {
    private static final float DEFAULT_ALPHA_FROM = 0.0f;
    private final float mFrom;

    public AlphaInAnimationAdapter(RecyclerView.Adapter adapter) {
        this(adapter, 0.0f);
    }

    public AlphaInAnimationAdapter(RecyclerView.Adapter adapter, float f) {
        super(adapter);
        this.mFrom = f;
    }

    @Override // com.luck.picture.lib.animators.BaseAnimationAdapter
    protected Animator[] getAnimators(View view2) {
        return new Animator[]{ObjectAnimator.ofFloat(view2, "alpha", this.mFrom, 1.0f)};
    }
}
