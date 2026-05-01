package com.aivox.base.databinding;

import android.view.View;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class ItemBindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected final T mBinding;
    public OnItemClickListener mOnItemClickListener;
    public View.OnLongClickListener mOnItemLongClickListener;

    public ItemBindingViewHolder(T t) {
        super(t.getRoot());
        this.mBinding = t;
    }

    public T getmBinding() {
        return this.mBinding;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view2) {
        OnItemClickListener onItemClickListener = this.mOnItemClickListener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view2, getPosition());
        }
    }
}
