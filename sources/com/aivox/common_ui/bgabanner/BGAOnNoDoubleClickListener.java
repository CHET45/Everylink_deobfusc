package com.aivox.common_ui.bgabanner;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public abstract class BGAOnNoDoubleClickListener implements View.OnClickListener {
    private long mLastClickTime;
    private int mThrottleFirstTime;

    public abstract void onNoDoubleClick(View view2);

    public BGAOnNoDoubleClickListener() {
        this.mThrottleFirstTime = 1000;
        this.mLastClickTime = 0L;
    }

    public BGAOnNoDoubleClickListener(int i) {
        this.mLastClickTime = 0L;
        this.mThrottleFirstTime = i;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view2) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.mLastClickTime > this.mThrottleFirstTime) {
            this.mLastClickTime = jCurrentTimeMillis;
            onNoDoubleClick(view2);
        }
    }
}
