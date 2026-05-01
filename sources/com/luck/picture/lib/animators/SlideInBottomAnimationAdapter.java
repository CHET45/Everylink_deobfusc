package com.luck.picture.lib.animators;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes3.dex */
public class SlideInBottomAnimationAdapter extends BaseAnimationAdapter {
    public SlideInBottomAnimationAdapter(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override // com.luck.picture.lib.animators.BaseAnimationAdapter
    protected Animator[] getAnimators(View view2) {
        return new Animator[]{ObjectAnimator.ofFloat(view2, "translationY", view2.getMeasuredHeight(), 0.0f)};
    }
}
