package com.aivox.common_ui.bgabanner.transformer;

import android.view.View;
import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: classes.dex */
public class FlipPageTransformer extends BGAPageTransformer {
    private static final float ROTATION = 180.0f;

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleInvisiblePage(View view2, float f) {
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleLeftPage(View view2, float f) {
        ViewCompat.setTranslationX(view2, (-view2.getWidth()) * f);
        ViewCompat.setRotationY(view2, ROTATION * f);
        if (f > -0.5d) {
            view2.setVisibility(0);
        } else {
            view2.setVisibility(4);
        }
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleRightPage(View view2, float f) {
        ViewCompat.setTranslationX(view2, (-view2.getWidth()) * f);
        ViewCompat.setRotationY(view2, ROTATION * f);
        if (f < 0.5d) {
            view2.setVisibility(0);
        } else {
            view2.setVisibility(4);
        }
    }
}
