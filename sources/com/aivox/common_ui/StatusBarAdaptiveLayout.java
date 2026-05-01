package com.aivox.common_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.blankj.utilcode.util.BarUtils;

/* JADX INFO: loaded from: classes.dex */
public class StatusBarAdaptiveLayout extends FrameLayout {
    public StatusBarAdaptiveLayout(Context context) {
        super(context);
        init();
    }

    public StatusBarAdaptiveLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public StatusBarAdaptiveLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setPadding(0, BarUtils.getStatusBarHeight(), 0, 0);
    }
}
