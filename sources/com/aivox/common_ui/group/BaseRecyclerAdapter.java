package com.aivox.common_ui.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {
    protected LayoutInflater mInflater;
    protected List<T> mItems = new ArrayList();
    private OnClickListener onClickListener = new OnClickListener() { // from class: com.aivox.common_ui.group.BaseRecyclerAdapter.1
        @Override // com.aivox.common_ui.group.BaseRecyclerAdapter.OnClickListener
        public void onClick(int i, long j) {
            if (BaseRecyclerAdapter.this.onItemClickListener != null) {
                BaseRecyclerAdapter.this.onItemClickListener.onItemClick(i, j);
            }
        }
    };
    private OnItemClickListener onItemClickListener;

    interface OnItemClickListener {
        void onItemClick(int i, long j);
    }

    protected abstract void onBindViewHolder(RecyclerView.ViewHolder viewHolder, T t, int i);

    protected abstract RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup viewGroup, int i);

    public BaseRecyclerAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolderOnCreateDefaultViewHolder = onCreateDefaultViewHolder(viewGroup, i);
        viewHolderOnCreateDefaultViewHolder.itemView.setTag(viewHolderOnCreateDefaultViewHolder);
        viewHolderOnCreateDefaultViewHolder.itemView.setOnClickListener(this.onClickListener);
        return viewHolderOnCreateDefaultViewHolder;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        onBindViewHolder(viewHolder, this.mItems.get(i), i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mItems.size();
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    void addAll(List<T> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        this.mItems.addAll(list);
        notifyItemRangeInserted(this.mItems.size(), list.size());
    }

    final void addItem(T t) {
        if (t != null) {
            this.mItems.add(t);
            notifyItemChanged(this.mItems.size());
        }
    }

    final List<T> getItems() {
        return this.mItems;
    }

    final T getItem(int i) {
        if (i < 0 || i >= this.mItems.size()) {
            return null;
        }
        return this.mItems.get(i);
    }

    static abstract class OnClickListener implements View.OnClickListener {
        public abstract void onClick(int i, long j);

        OnClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view2) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view2.getTag();
            onClick(viewHolder.getAdapterPosition(), viewHolder.getItemId());
        }
    }

    public final void removeItem(T t) {
        if (this.mItems.contains(t)) {
            int iIndexOf = this.mItems.indexOf(t);
            this.mItems.remove(t);
            notifyItemRemoved(iIndexOf);
        }
    }

    protected final void removeItem(int i) {
        if (getItemCount() > i) {
            this.mItems.remove(i);
            notifyItemRemoved(i);
        }
    }

    protected final void clear() {
        this.mItems.clear();
        notifyDataSetChanged();
    }
}
