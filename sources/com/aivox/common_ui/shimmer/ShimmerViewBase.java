package com.aivox.common_ui.shimmer;

import com.aivox.common_ui.shimmer.ShimmerViewHelper;

/* JADX INFO: loaded from: classes.dex */
public interface ShimmerViewBase {
    float getGradientX();

    int getPrimaryColor();

    int getReflectionColor();

    boolean isSetUp();

    boolean isShimmering();

    void setAnimationSetupCallback(ShimmerViewHelper.AnimationSetupCallback animationSetupCallback);

    void setGradientX(float f);

    void setPrimaryColor(int i);

    void setReflectionColor(int i);

    void setShimmering(boolean z);
}
