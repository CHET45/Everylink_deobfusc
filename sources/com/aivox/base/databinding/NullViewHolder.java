package com.aivox.base.databinding;

import android.view.View;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class NullViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder implements OnNullViewItemClickListener {
    protected final T mBinding;
    OnNullViewItemClickListener onNullViewItemClickListener;

    public T getmBinding() {
        return this.mBinding;
    }

    public NullViewHolder(T t) {
        super(t.getRoot());
        this.mBinding = t;
    }

    @Override // com.aivox.base.databinding.OnNullViewItemClickListener
    public void onItemClick(View view2, int i) {
        OnNullViewItemClickListener onNullViewItemClickListener = this.onNullViewItemClickListener;
        if (onNullViewItemClickListener != null) {
            onNullViewItemClickListener.onItemClick(view2, i);
        }
    }
}
