package com.aivox.app.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.base.C0874R;
import com.aivox.base.databinding.ItemBindingViewHolder;
import com.aivox.base.databinding.MyBindingAdapter;
import com.aivox.base.databinding.NullViewHolder;
import com.aivox.common.model.AudioInfoBean;

/* JADX INFO: loaded from: classes.dex */
public class RecordImportAdapter extends MyBindingAdapter<AudioInfoBean> {
    Context context;

    public RecordImportAdapter(Context context, int i) {
        super(context, i);
        this.context = context;
    }

    @Override // com.aivox.base.databinding.MyBindingAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof ItemBindingViewHolder) {
            if (this.mData == null || this.mData.size() <= 0 || i + 1 > this.mData.size()) {
                return;
            }
            ItemBindingViewHolder itemBindingViewHolder = (ItemBindingViewHolder) viewHolder;
            itemBindingViewHolder.getmBinding().setVariable(6, this.mData.get(i));
            itemBindingViewHolder.getmBinding().executePendingBindings();
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.adapter.RecordImportAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m239x50ebdb1f(i, view2);
                }
            });
            return;
        }
        if ((viewHolder instanceof NullViewHolder) && i == 0) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.adapter.RecordImportAdapter$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m240x362d49e0(i, view2);
                }
            });
            ((ImageView) viewHolder.itemView.findViewById(C0726R.id.f116iv)).setImageResource(C0874R.mipmap.ic_empty_list);
        }
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$0$com-aivox-app-adapter-RecordImportAdapter */
    /* synthetic */ void m239x50ebdb1f(int i, View view2) {
        if (this.mOnItemClickListener != null) {
            this.mOnItemClickListener.onItemClick(view2, i);
        }
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$1$com-aivox-app-adapter-RecordImportAdapter */
    /* synthetic */ void m240x362d49e0(int i, View view2) {
        if (this.onNullViewItemClickListener != null) {
            this.onNullViewItemClickListener.onItemClick(view2, i);
        }
    }
}
