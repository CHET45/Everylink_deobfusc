package com.aivox.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/* JADX INFO: loaded from: classes.dex */
public class MaxHeightListView extends ListView {
    private int listViewHeight;

    public int getListViewHeight() {
        return this.listViewHeight;
    }

    public void setListViewHeight(int i) {
        this.listViewHeight = i;
    }

    public MaxHeightListView(Context context) {
        super(context);
    }

    public MaxHeightListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public MaxHeightListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.View
    protected void onMeasure(int i, int i2) {
        int i3 = this.listViewHeight;
        if (i3 > -1) {
            i2 = View.MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE);
        }
        super.onMeasure(i, i2);
    }
}
