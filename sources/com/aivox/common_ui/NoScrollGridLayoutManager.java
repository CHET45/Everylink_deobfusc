package com.aivox.common_ui;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.GridLayoutManager;

/* JADX INFO: loaded from: classes.dex */
public class NoScrollGridLayoutManager extends GridLayoutManager {
    private boolean isScrollEnabled;

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return false;
    }

    public NoScrollGridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.isScrollEnabled = true;
    }

    public NoScrollGridLayoutManager(Context context, int i) {
        super(context, i);
        this.isScrollEnabled = true;
    }

    public NoScrollGridLayoutManager(Context context, int i, int i2, boolean z) {
        super(context, i, i2, z);
        this.isScrollEnabled = true;
    }
}
