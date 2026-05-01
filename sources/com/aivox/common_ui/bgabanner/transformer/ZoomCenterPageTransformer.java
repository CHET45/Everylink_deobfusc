package com.aivox.common_ui.bgabanner.transformer;

import android.view.View;
import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: classes.dex */
public class ZoomCenterPageTransformer extends BGAPageTransformer {
    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleInvisiblePage(View view2, float f) {
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleLeftPage(View view2, float f) {
        ViewCompat.setTranslationX(view2, (-view2.getWidth()) * f);
        ViewCompat.setPivotX(view2, view2.getWidth() * 0.5f);
        ViewCompat.setPivotY(view2, view2.getHeight() * 0.5f);
        float f2 = f + 1.0f;
        ViewCompat.setScaleX(view2, f2);
        ViewCompat.setScaleY(view2, f2);
        if (f < -0.95f) {
            ViewCompat.setAlpha(view2, 0.0f);
        } else {
            ViewCompat.setAlpha(view2, 1.0f);
        }
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleRightPage(View view2, float f) {
        ViewCompat.setTranslationX(view2, (-view2.getWidth()) * f);
        ViewCompat.setPivotX(view2, view2.getWidth() * 0.5f);
        ViewCompat.setPivotY(view2, view2.getHeight() * 0.5f);
        float f2 = 1.0f - f;
        ViewCompat.setScaleX(view2, f2);
        ViewCompat.setScaleY(view2, f2);
        if (f > 0.95f) {
            ViewCompat.setAlpha(view2, 0.0f);
        } else {
            ViewCompat.setAlpha(view2, 1.0f);
        }
    }
}
