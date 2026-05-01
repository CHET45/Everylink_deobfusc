package com.aivox.common_ui.bgabanner.transformer;

import android.view.View;
import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: classes.dex */
public class DepthPageTransformer extends BGAPageTransformer {
    private float mMinScale = 0.8f;

    public DepthPageTransformer() {
    }

    public DepthPageTransformer(float f) {
        setMinScale(f);
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleInvisiblePage(View view2, float f) {
        ViewCompat.setAlpha(view2, 0.0f);
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleLeftPage(View view2, float f) {
        ViewCompat.setAlpha(view2, 1.0f);
        ViewCompat.setTranslationX(view2, 0.0f);
        ViewCompat.setScaleX(view2, 1.0f);
        ViewCompat.setScaleY(view2, 1.0f);
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleRightPage(View view2, float f) {
        float f2 = 1.0f - f;
        ViewCompat.setAlpha(view2, f2);
        ViewCompat.setTranslationX(view2, (-view2.getWidth()) * f);
        float f3 = this.mMinScale;
        float f4 = f3 + ((1.0f - f3) * f2);
        ViewCompat.setScaleX(view2, f4);
        ViewCompat.setScaleY(view2, f4);
    }

    public void setMinScale(float f) {
        if (f < 0.6f || f > 1.0f) {
            return;
        }
        this.mMinScale = f;
    }
}
