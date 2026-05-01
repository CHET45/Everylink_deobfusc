package com.aivox.common_ui.bgabanner.transformer;

import android.view.View;
import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: classes.dex */
public class AlphaPageTransformer extends BGAPageTransformer {
    private float mMinScale = 0.4f;

    public AlphaPageTransformer() {
    }

    public AlphaPageTransformer(float f) {
        setMinScale(f);
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleInvisiblePage(View view2, float f) {
        ViewCompat.setAlpha(view2, 0.0f);
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleLeftPage(View view2, float f) {
        float f2 = this.mMinScale;
        ViewCompat.setAlpha(view2, f2 + ((1.0f - f2) * (f + 1.0f)));
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleRightPage(View view2, float f) {
        float f2 = this.mMinScale;
        ViewCompat.setAlpha(view2, f2 + ((1.0f - f2) * (1.0f - f)));
    }

    public void setMinScale(float f) {
        if (f < 0.0f || f > 1.0f) {
            return;
        }
        this.mMinScale = f;
    }
}
