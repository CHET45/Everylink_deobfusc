package com.aivox.common_ui.bgabanner.transformer;

import android.view.View;
import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: classes.dex */
public class CubePageTransformer extends BGAPageTransformer {
    private float mMaxRotation = 90.0f;

    public CubePageTransformer() {
    }

    public CubePageTransformer(float f) {
        setMaxRotation(f);
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleInvisiblePage(View view2, float f) {
        ViewCompat.setPivotX(view2, view2.getMeasuredWidth());
        ViewCompat.setPivotY(view2, view2.getMeasuredHeight() * 0.5f);
        ViewCompat.setRotationY(view2, 0.0f);
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleLeftPage(View view2, float f) {
        ViewCompat.setPivotX(view2, view2.getMeasuredWidth());
        ViewCompat.setPivotY(view2, view2.getMeasuredHeight() * 0.5f);
        ViewCompat.setRotationY(view2, this.mMaxRotation * f);
    }

    @Override // com.aivox.common_ui.bgabanner.transformer.BGAPageTransformer
    public void handleRightPage(View view2, float f) {
        ViewCompat.setPivotX(view2, 0.0f);
        ViewCompat.setPivotY(view2, view2.getMeasuredHeight() * 0.5f);
        ViewCompat.setRotationY(view2, this.mMaxRotation * f);
    }

    public void setMaxRotation(float f) {
        if (f < 0.0f || f > 90.0f) {
            return;
        }
        this.mMaxRotation = f;
    }
}
