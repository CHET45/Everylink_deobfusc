package com.daimajia.swipe.implments;

import android.view.View;
import android.widget.BaseAdapter;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemMangerImpl;

/* JADX INFO: loaded from: classes3.dex */
public class SwipeItemAdapterMangerImpl extends SwipeItemMangerImpl {
    protected BaseAdapter mAdapter;

    @Override // com.daimajia.swipe.implments.SwipeItemMangerImpl
    public void bindView(View view2, int i) {
    }

    public SwipeItemAdapterMangerImpl(BaseAdapter baseAdapter) {
        super(baseAdapter);
        this.mAdapter = baseAdapter;
    }

    @Override // com.daimajia.swipe.implments.SwipeItemMangerImpl
    public void initialize(View view2, int i) {
        int swipeLayoutId = getSwipeLayoutId(i);
        SwipeItemMangerImpl.OnLayoutListener onLayoutListener = new SwipeItemMangerImpl.OnLayoutListener(i);
        SwipeLayout swipeLayout = (SwipeLayout) view2.findViewById(swipeLayoutId);
        if (swipeLayout == null) {
            throw new IllegalStateException("can not find SwipeLayout in target view");
        }
        SwipeItemMangerImpl.SwipeMemory swipeMemory = new SwipeItemMangerImpl.SwipeMemory(i);
        swipeLayout.addSwipeListener(swipeMemory);
        swipeLayout.addOnLayoutListener(onLayoutListener);
        swipeLayout.setTag(swipeLayoutId, new SwipeItemMangerImpl.ValueBox(i, swipeMemory, onLayoutListener));
        this.mShownLayouts.add(swipeLayout);
    }

    @Override // com.daimajia.swipe.implments.SwipeItemMangerImpl
    public void updateConvertView(View view2, int i) {
        int swipeLayoutId = getSwipeLayoutId(i);
        SwipeLayout swipeLayout = (SwipeLayout) view2.findViewById(swipeLayoutId);
        if (swipeLayout == null) {
            throw new IllegalStateException("can not find SwipeLayout in target view");
        }
        SwipeItemMangerImpl.ValueBox valueBox = (SwipeItemMangerImpl.ValueBox) swipeLayout.getTag(swipeLayoutId);
        valueBox.swipeMemory.setPosition(i);
        valueBox.onLayoutListener.setPosition(i);
        valueBox.position = i;
    }
}
