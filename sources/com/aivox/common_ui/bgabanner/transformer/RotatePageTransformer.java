package com.aivox.common_ui.bgabanner.transformer;

import android.view.View;
import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: classes.dex */
public class RotatePageTransformer extends BGAPageTransformer {
    private float mMaxRotation = 15.0f;

    public RotatePageTransformer() {
    }

    public RotatePageTransformer(float f) {
        setMaxRotation(f);
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleInvisiblePage(View view2, float f) {
        ViewCompat.setPivotX(view2, view2.getMeasuredWidth() * 0.5f);
        ViewCompat.setPivotY(view2, view2.getMeasuredHeight());
        ViewCompat.setRotation(view2, 0.0f);
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleLeftPage(View view2, float f) {
        float f2 = this.mMaxRotation * f;
        ViewCompat.setPivotX(view2, view2.getMeasuredWidth() * 0.5f);
        ViewCompat.setPivotY(view2, view2.getMeasuredHeight());
        ViewCompat.setRotation(view2, f2);
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleRightPage(View view2, float f) {
        handleLeftPage(view2, f);
    }

    public void setMaxRotation(float f) {
        if (f < 0.0f || f > 40.0f) {
            return;
        }
        this.mMaxRotation = f;
    }
}
