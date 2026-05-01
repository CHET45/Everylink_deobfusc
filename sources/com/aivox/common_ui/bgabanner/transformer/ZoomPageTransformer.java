package com.aivox.common_ui.bgabanner.transformer;

import android.view.View;
import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: classes.dex */
public class ZoomPageTransformer extends BGAPageTransformer {
    private float mMinScale = 0.85f;
    private float mMinAlpha = 0.65f;

    public ZoomPageTransformer() {
    }

    public ZoomPageTransformer(float f, float f2) {
        setMinAlpha(f);
        setMinScale(f2);
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleInvisiblePage(View view2, float f) {
        ViewCompat.setAlpha(view2, 0.0f);
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleLeftPage(View view2, float f) {
        float fMax = Math.max(this.mMinScale, f + 1.0f);
        float f2 = 1.0f - fMax;
        ViewCompat.setTranslationX(view2, ((view2.getWidth() * f2) / 2.0f) - (((view2.getHeight() * f2) / 2.0f) / 2.0f));
        ViewCompat.setScaleX(view2, fMax);
        ViewCompat.setScaleY(view2, fMax);
        float f3 = this.mMinAlpha;
        float f4 = this.mMinScale;
        ViewCompat.setAlpha(view2, f3 + (((fMax - f4) / (1.0f - f4)) * (1.0f - f3)));
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleRightPage(View view2, float f) {
        float fMax = Math.max(this.mMinScale, 1.0f - f);
        float f2 = 1.0f - fMax;
        ViewCompat.setTranslationX(view2, (-((view2.getWidth() * f2) / 2.0f)) + (((view2.getHeight() * f2) / 2.0f) / 2.0f));
        ViewCompat.setScaleX(view2, fMax);
        ViewCompat.setScaleY(view2, fMax);
        float f3 = this.mMinAlpha;
        float f4 = this.mMinScale;
        ViewCompat.setAlpha(view2, f3 + (((fMax - f4) / (1.0f - f4)) * (1.0f - f3)));
    }

    public void setMinAlpha(float f) {
        if (f < 0.6f || f > 1.0f) {
            return;
        }
        this.mMinAlpha = f;
    }

    public void setMinScale(float f) {
        if (f < 0.6f || f > 1.0f) {
            return;
        }
        this.mMinScale = f;
    }
}
