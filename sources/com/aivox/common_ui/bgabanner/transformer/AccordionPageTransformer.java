package com.aivox.common_ui.bgabanner.transformer;

import android.view.View;
import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: classes.dex */
public class AccordionPageTransformer extends BGAPageTransformer {
    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleInvisiblePage(View view2, float f) {
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleLeftPage(View view2, float f) {
        ViewCompat.setPivotX(view2, view2.getWidth());
        ViewCompat.setScaleX(view2, f + 1.0f);
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleRightPage(View view2, float f) {
        ViewCompat.setPivotX(view2, 0.0f);
        ViewCompat.setScaleX(view2, 1.0f - f);
        ViewCompat.setAlpha(view2, 1.0f);
    }
}
