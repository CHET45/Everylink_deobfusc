package com.aivox.set.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.common_ui.AutoSwipeRefreshView;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.set.C1106R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityPurchaseHistoryBinding extends ViewDataBinding {
    public final AutoSwipeRefreshView refreshView;
    public final RecyclerView rvList;
    public final HeadTitleLinearView titleView;
    public final TextView tvHistory;

    protected ActivityPurchaseHistoryBinding(Object obj, View view2, int i, AutoSwipeRefreshView autoSwipeRefreshView, RecyclerView recyclerView, HeadTitleLinearView headTitleLinearView, TextView textView) {
        super(obj, view2, i);
        this.refreshView = autoSwipeRefreshView;
        this.rvList = recyclerView;
        this.titleView = headTitleLinearView;
        this.tvHistory = textView;
    }

    public static ActivityPurchaseHistoryBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityPurchaseHistoryBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityPurchaseHistoryBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.activity_purchase_history, viewGroup, z, obj);
    }

    public static ActivityPurchaseHistoryBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityPurchaseHistoryBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityPurchaseHistoryBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.activity_purchase_history, null, false, obj);
    }

    public static ActivityPurchaseHistoryBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityPurchaseHistoryBinding bind(View view2, Object obj) {
        return (ActivityPurchaseHistoryBinding) bind(obj, view2, C1106R.layout.activity_purchase_history);
    }
}
