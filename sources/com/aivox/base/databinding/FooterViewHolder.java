package com.aivox.base.databinding;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class FooterViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    protected final T mBinding;

    public T getmBinding() {
        return this.mBinding;
    }

    public FooterViewHolder(T t) {
        super(t.getRoot());
        this.mBinding = t;
    }
}
