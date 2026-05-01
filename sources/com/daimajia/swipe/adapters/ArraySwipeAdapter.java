package com.daimajia.swipe.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemAdapterMangerImpl;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import com.daimajia.swipe.util.Attributes;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ArraySwipeAdapter<T> extends ArrayAdapter implements SwipeItemMangerInterface, SwipeAdapterInterface {
    private SwipeItemAdapterMangerImpl mItemManger;

    public ArraySwipeAdapter(Context context, int i) {
        super(context, i);
        this.mItemManger = new SwipeItemAdapterMangerImpl(this);
    }

    public ArraySwipeAdapter(Context context, int i, int i2) {
        super(context, i, i2);
        this.mItemManger = new SwipeItemAdapterMangerImpl(this);
    }

    public ArraySwipeAdapter(Context context, int i, T[] tArr) {
        super(context, i, tArr);
        this.mItemManger = new SwipeItemAdapterMangerImpl(this);
    }

    public ArraySwipeAdapter(Context context, int i, int i2, T[] tArr) {
        super(context, i, i2, tArr);
        this.mItemManger = new SwipeItemAdapterMangerImpl(this);
    }

    public ArraySwipeAdapter(Context context, int i, List<T> list) {
        super(context, i, list);
        this.mItemManger = new SwipeItemAdapterMangerImpl(this);
    }

    public ArraySwipeAdapter(Context context, int i, int i2, List<T> list) {
        super(context, i, i2, list);
        this.mItemManger = new SwipeItemAdapterMangerImpl(this);
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view2, ViewGroup viewGroup) {
        boolean z = view2 == null;
        View view3 = super.getView(i, view2, viewGroup);
        if (z) {
            this.mItemManger.initialize(view3, i);
        } else {
            this.mItemManger.updateConvertView(view3, i);
        }
        return view3;
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void openItem(int i) {
        this.mItemManger.openItem(i);
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void closeItem(int i) {
        this.mItemManger.closeItem(i);
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void closeAllExcept(SwipeLayout swipeLayout) {
        this.mItemManger.closeAllExcept(swipeLayout);
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void closeAllItems() {
        this.mItemManger.closeAllItems();
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public List<Integer> getOpenItems() {
        return this.mItemManger.getOpenItems();
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public List<SwipeLayout> getOpenLayouts() {
        return this.mItemManger.getOpenLayouts();
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void removeShownLayouts(SwipeLayout swipeLayout) {
        this.mItemManger.removeShownLayouts(swipeLayout);
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public boolean isOpen(int i) {
        return this.mItemManger.isOpen(i);
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public Attributes.Mode getMode() {
        return this.mItemManger.getMode();
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void setMode(Attributes.Mode mode) {
        this.mItemManger.setMode(mode);
    }
}
