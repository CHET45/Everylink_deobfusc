package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.common_ui.AutoSwipeRefreshView;

/* JADX INFO: loaded from: classes.dex */
public abstract class FragmentLocalRecordBinding extends ViewDataBinding {
    public final LinearLayout llEmpty;
    public final RecyclerView rvAudio;
    public final AutoSwipeRefreshView srvAudio;

    /* JADX INFO: renamed from: tv */
    public final TextView f122tv;

    protected FragmentLocalRecordBinding(Object obj, View view2, int i, LinearLayout linearLayout, RecyclerView recyclerView, AutoSwipeRefreshView autoSwipeRefreshView, TextView textView) {
        super(obj, view2, i);
        this.llEmpty = linearLayout;
        this.rvAudio = recyclerView;
        this.srvAudio = autoSwipeRefreshView;
        this.f122tv = textView;
    }

    public static FragmentLocalRecordBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentLocalRecordBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FragmentLocalRecordBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_local_record, viewGroup, z, obj);
    }

    public static FragmentLocalRecordBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentLocalRecordBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FragmentLocalRecordBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_local_record, null, false, obj);
    }

    public static FragmentLocalRecordBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentLocalRecordBinding bind(View view2, Object obj) {
        return (FragmentLocalRecordBinding) bind(obj, view2, C0726R.layout.fragment_local_record);
    }
}
