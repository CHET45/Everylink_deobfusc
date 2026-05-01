package com.daimajia.swipe.implments;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemMangerImpl;

/* JADX INFO: loaded from: classes3.dex */
public class SwipeItemRecyclerMangerImpl extends SwipeItemMangerImpl {
    protected RecyclerView.Adapter mAdapter;

    @Override // com.daimajia.swipe.implments.SwipeItemMangerImpl
    public void initialize(View view2, int i) {
    }

    @Override // com.daimajia.swipe.implments.SwipeItemMangerImpl
    public void updateConvertView(View view2, int i) {
    }

    public SwipeItemRecyclerMangerImpl(RecyclerView.Adapter adapter) {
        super(adapter);
        this.mAdapter = adapter;
    }

    @Override // com.daimajia.swipe.implments.SwipeItemMangerImpl
    public void bindView(View view2, int i) {
        int swipeLayoutId = getSwipeLayoutId(i);
        SwipeItemMangerImpl.OnLayoutListener onLayoutListener = new SwipeItemMangerImpl.OnLayoutListener(i);
        SwipeLayout swipeLayout = (SwipeLayout) view2.findViewById(swipeLayoutId);
        if (swipeLayout == null) {
            throw new IllegalStateException("can not find SwipeLayout in target view");
        }
        if (swipeLayout.getTag(swipeLayoutId) == null) {
            SwipeItemMangerImpl.SwipeMemory swipeMemory = new SwipeItemMangerImpl.SwipeMemory(i);
            swipeLayout.addSwipeListener(swipeMemory);
            swipeLayout.addOnLayoutListener(onLayoutListener);
            swipeLayout.setTag(swipeLayoutId, new SwipeItemMangerImpl.ValueBox(i, swipeMemory, onLayoutListener));
            this.mShownLayouts.add(swipeLayout);
            return;
        }
        SwipeItemMangerImpl.ValueBox valueBox = (SwipeItemMangerImpl.ValueBox) swipeLayout.getTag(swipeLayoutId);
        valueBox.swipeMemory.setPosition(i);
        valueBox.onLayoutListener.setPosition(i);
        valueBox.position = i;
    }
}
