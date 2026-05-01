package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.common_ui.AutoSwipeRefreshView;

/* JADX INFO: loaded from: classes.dex */
public abstract class FragmentCloudRecordBinding extends ViewDataBinding {
    public final RecyclerView audioRv;
    public final ConstraintLayout clMain;
    public final AutoSwipeRefreshView refreshLayout;

    protected FragmentCloudRecordBinding(Object obj, View view2, int i, RecyclerView recyclerView, ConstraintLayout constraintLayout, AutoSwipeRefreshView autoSwipeRefreshView) {
        super(obj, view2, i);
        this.audioRv = recyclerView;
        this.clMain = constraintLayout;
        this.refreshLayout = autoSwipeRefreshView;
    }

    public static FragmentCloudRecordBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentCloudRecordBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FragmentCloudRecordBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_cloud_record, viewGroup, z, obj);
    }

    public static FragmentCloudRecordBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentCloudRecordBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FragmentCloudRecordBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_cloud_record, null, false, obj);
    }

    public static FragmentCloudRecordBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentCloudRecordBinding bind(View view2, Object obj) {
        return (FragmentCloudRecordBinding) bind(obj, view2, C0726R.layout.fragment_cloud_record);
    }
}
