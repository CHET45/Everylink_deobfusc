package com.luck.picture.lib.animators;

import android.view.View;
import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: classes3.dex */
public final class ViewHelper {
    public static void clear(View view2) {
        view2.setAlpha(1.0f);
        view2.setScaleY(1.0f);
        view2.setScaleX(1.0f);
        view2.setTranslationY(0.0f);
        view2.setTranslationX(0.0f);
        view2.setRotation(0.0f);
        view2.setRotationY(0.0f);
        view2.setRotationX(0.0f);
        view2.setPivotY(view2.getMeasuredHeight() / 2);
        view2.setPivotX(view2.getMeasuredWidth() / 2);
        ViewCompat.animate(view2).setInterpolator(null).setStartDelay(0L);
    }
}
