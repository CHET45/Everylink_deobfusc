package com.lcodecore.tkrefreshlayout;

import android.view.View;

/* JADX INFO: loaded from: classes3.dex */
public interface IBottomView {
    View getView();

    void onFinish();

    void onPullReleasing(float f, float f2, float f3);

    void onPullingUp(float f, float f2, float f3);

    void reset();

    void startAnim(float f, float f2);
}
