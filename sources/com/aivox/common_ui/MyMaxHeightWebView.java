package com.aivox.common_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;

/* JADX INFO: loaded from: classes.dex */
public class MyMaxHeightWebView extends WebView {
    private int listViewHeight;

    public MyMaxHeightWebView(Context context) {
        super(context);
    }

    public MyMaxHeightWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MyMaxHeightWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public MyMaxHeightWebView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public int getListViewHeight() {
        return this.listViewHeight;
    }

    public void setListViewHeight(int i) {
        this.listViewHeight = i;
    }

    @Override // android.webkit.WebView, android.widget.AbsoluteLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int i3 = this.listViewHeight;
        if (i3 > -1) {
            i2 = View.MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE);
        }
        super.onMeasure(i, i2);
    }
}
