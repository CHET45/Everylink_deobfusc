package com.aivox.base.databinding;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class HeaderBindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    protected final T mBinding;

    public HeaderBindingViewHolder(T t) {
        super(t.getRoot());
        this.mBinding = t;
    }

    public T getmBinding() {
        return this.mBinding;
    }
}
