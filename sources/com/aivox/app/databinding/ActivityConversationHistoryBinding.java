package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityConversationHistoryBinding extends ViewDataBinding {
    public final LinearLayout main;
    public final RecyclerView rvHistory;

    protected ActivityConversationHistoryBinding(Object obj, View view2, int i, LinearLayout linearLayout, RecyclerView recyclerView) {
        super(obj, view2, i);
        this.main = linearLayout;
        this.rvHistory = recyclerView;
    }

    public static ActivityConversationHistoryBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityConversationHistoryBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityConversationHistoryBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_conversation_history, viewGroup, z, obj);
    }

    public static ActivityConversationHistoryBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityConversationHistoryBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityConversationHistoryBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_conversation_history, null, false, obj);
    }

    public static ActivityConversationHistoryBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityConversationHistoryBinding bind(View view2, Object obj) {
        return (ActivityConversationHistoryBinding) bind(obj, view2, C0726R.layout.activity_conversation_history);
    }
}
