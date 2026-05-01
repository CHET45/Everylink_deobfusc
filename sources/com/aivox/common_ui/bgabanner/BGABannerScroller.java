package com.aivox.common_ui.bgabanner;

import android.content.Context;
import android.widget.Scroller;

/* JADX INFO: loaded from: classes.dex */
public class BGABannerScroller extends Scroller {
    private int mDuration;

    public BGABannerScroller(Context context, int i) {
        super(context);
        this.mDuration = i;
    }

    @Override // android.widget.Scroller
    public void startScroll(int i, int i2, int i3, int i4) {
        super.startScroll(i, i2, i3, i4, this.mDuration);
    }

    @Override // android.widget.Scroller
    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        super.startScroll(i, i2, i3, i4, this.mDuration);
    }
}
