package com.github.gzuliyujiang.wheelview.contract;

import com.github.gzuliyujiang.wheelview.widget.WheelView;

/* JADX INFO: loaded from: classes3.dex */
public interface OnWheelChangedListener {
    void onWheelLoopFinished(WheelView view2);

    void onWheelScrollStateChanged(WheelView view2, int state);

    void onWheelScrolled(WheelView view2, int offset);

    void onWheelSelected(WheelView view2, int position);
}
