package com.aivox.common_ui.cityselect.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/* JADX INFO: loaded from: classes.dex */
public class WrapHeightGridView extends GridView {
    public WrapHeightGridView(Context context) {
        this(context, null);
    }

    public WrapHeightGridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WrapHeightGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
    }
}
