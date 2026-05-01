package com.aivox.common_ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
    private int inset;
    private boolean isLastLineShow;
    private Drawable mDivider;
    private int mDividerHeight;

    public SimpleDividerItemDecoration(Context context, int i) {
        this.mDivider = ContextCompat.getDrawable(context, C1034R.drawable.line_divider);
        this.mDividerHeight = i;
    }

    public SimpleDividerItemDecoration(Context context, int i, int i2, int i3) {
        this.inset = i;
        this.mDivider = ContextCompat.getDrawable(context, i3);
        this.mDividerHeight = i2;
    }

    public SimpleDividerItemDecoration(Context context, int i, int i2) {
        this.inset = i;
        this.mDivider = ContextCompat.getDrawable(context, C1034R.drawable.line_divider);
        this.mDividerHeight = i2;
    }

    public SimpleDividerItemDecoration(Context context, Drawable drawable, int i) {
        if (drawable == null) {
            this.mDivider = ContextCompat.getDrawable(context, C1034R.drawable.line_divider);
        } else {
            this.mDivider = drawable;
        }
        this.mDividerHeight = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
        super.getItemOffsets(rect, view2, recyclerView, state);
        rect.set(0, 0, 0, this.mDividerHeight);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        int paddingLeft = recyclerView.getPaddingLeft();
        int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
        int childCount = recyclerView.getChildCount();
        int i = 0;
        while (true) {
            if (i >= (this.isLastLineShow ? childCount : childCount - 1)) {
                return;
            }
            View childAt = recyclerView.getChildAt(i);
            int bottom = childAt.getBottom() + ((RecyclerView.LayoutParams) childAt.getLayoutParams()).bottomMargin;
            int intrinsicHeight = this.mDivider.getIntrinsicHeight() + bottom;
            int i2 = this.inset;
            if (i2 > 0) {
                this.mDivider.setBounds(paddingLeft + i2, bottom, width - i2, intrinsicHeight);
            } else {
                this.mDivider.setBounds(paddingLeft, bottom, width, intrinsicHeight);
            }
            this.mDivider.draw(canvas);
            i++;
        }
    }

    public void setLastLineShow(boolean z) {
        this.isLastLineShow = z;
    }
}
