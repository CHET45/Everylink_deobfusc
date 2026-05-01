package com.github.ybq.android.spinkit.animation.interpolator;

import android.graphics.Path;
import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes3.dex */
public class PathInterpolatorCompat {
    private PathInterpolatorCompat() {
    }

    public static Interpolator create(Path path) {
        return PathInterpolatorCompatApi21.create(path);
    }

    public static Interpolator create(float f, float f2) {
        return PathInterpolatorCompatApi21.create(f, f2);
    }

    public static Interpolator create(float f, float f2, float f3, float f4) {
        return PathInterpolatorCompatApi21.create(f, f2, f3, f4);
    }
}
