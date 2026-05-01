package com.lxj.easyadapter;

import android.util.SparseArray;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ItemDelegateManager.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(m1899bv = {1, 0, 3}, m1900d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006J\"\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\r\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006J#\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00028\u00002\u0006\u0010\u0013\u001a\u00020\b¢\u0006\u0002\u0010\u0014J\u000e\u0010\u0015\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\bJ\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u00062\u0006\u0010\r\u001a\u00020\bJ\u001b\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00028\u00002\u0006\u0010\u0013\u001a\u00020\b¢\u0006\u0002\u0010\u0018J\u0014\u0010\u0017\u001a\u00020\b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006J\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006J\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u001b\u001a\u00020\bR\u001a\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00060\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u001c"}, m1901d2 = {"Lcom/lxj/easyadapter/ItemDelegateManager;", ExifInterface.GPS_DIRECTION_TRUE, "", "()V", "delegates", "Landroid/util/SparseArray;", "Lcom/lxj/easyadapter/ItemDelegate;", "itemViewDelegateCount", "", "getItemViewDelegateCount", "()I", "addDelegate", "delegate", "viewType", "convert", "", "holder", "Lcom/lxj/easyadapter/ViewHolder;", "item", "position", "(Lcom/lxj/easyadapter/ViewHolder;Ljava/lang/Object;I)V", "getItemLayoutId", "getItemViewDelegate", "getItemViewType", "(Ljava/lang/Object;I)I", "itemViewDelegate", "removeDelegate", "itemType", "easy-adapter_release"}, m1902k = 1, m1903mv = {1, 1, 15})
public final class ItemDelegateManager<T> {
    private SparseArray<ItemDelegate<T>> delegates = new SparseArray<>();

    public final int getItemViewDelegateCount() {
        return this.delegates.size();
    }

    public final ItemDelegateManager<T> addDelegate(ItemDelegate<T> delegate) {
        Intrinsics.checkParameterIsNotNull(delegate, "delegate");
        this.delegates.put(this.delegates.size(), delegate);
        return this;
    }

    public final ItemDelegateManager<T> addDelegate(int viewType, ItemDelegate<T> delegate) {
        Intrinsics.checkParameterIsNotNull(delegate, "delegate");
        if (this.delegates.get(viewType) != null) {
            throw new IllegalArgumentException("An ItemDelegate is already registered for the viewType = " + viewType + ". Already registered ItemDelegate is " + this.delegates.get(viewType));
        }
        this.delegates.put(viewType, delegate);
        return this;
    }

    public final ItemDelegateManager<T> removeDelegate(ItemDelegate<T> delegate) {
        Intrinsics.checkParameterIsNotNull(delegate, "delegate");
        int iIndexOfValue = this.delegates.indexOfValue(delegate);
        if (iIndexOfValue >= 0) {
            this.delegates.removeAt(iIndexOfValue);
        }
        return this;
    }

    public final ItemDelegateManager<T> removeDelegate(int itemType) {
        int iIndexOfKey = this.delegates.indexOfKey(itemType);
        if (iIndexOfKey >= 0) {
            this.delegates.removeAt(iIndexOfKey);
        }
        return this;
    }

    public final int getItemViewType(T item, int position) {
        for (int size = this.delegates.size() - 1; size >= 0; size--) {
            if (this.delegates.valueAt(size).isThisType(item, position)) {
                return this.delegates.keyAt(size);
            }
        }
        throw new IllegalArgumentException("No ItemDelegate added that matches position=" + position + " in data source");
    }

    public final void convert(ViewHolder holder, T item, int position) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        int size = this.delegates.size();
        for (int i = 0; i < size; i++) {
            ItemDelegate<T> itemDelegateValueAt = this.delegates.valueAt(i);
            if (itemDelegateValueAt.isThisType(item, position)) {
                itemDelegateValueAt.bind(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException("No ItemDelegateManager added that matches position=" + position + " in data source");
    }

    public final ItemDelegate<T> getItemViewDelegate(int viewType) {
        ItemDelegate<T> itemDelegate = this.delegates.get(viewType);
        if (itemDelegate == null) {
            Intrinsics.throwNpe();
        }
        return itemDelegate;
    }

    public final int getItemLayoutId(int viewType) {
        return getItemViewDelegate(viewType).getLayoutId();
    }

    public final int getItemViewType(ItemDelegate<T> itemViewDelegate) {
        Intrinsics.checkParameterIsNotNull(itemViewDelegate, "itemViewDelegate");
        return this.delegates.indexOfValue(itemViewDelegate);
    }
}
