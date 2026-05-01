package com.aivox.app.util;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceItemDecoration(int i) {
        this.space = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
        super.getItemOffsets(rect, view2, recyclerView, state);
        if (recyclerView.getChildAdapterPosition(view2) == 0) {
            rect.bottom = this.space;
        } else {
            rect.bottom = 0;
        }
    }
}
