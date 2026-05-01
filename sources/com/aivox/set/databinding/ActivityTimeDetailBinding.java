package com.aivox.set.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.RemainTimeView;
import com.aivox.set.C1106R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityTimeDetailBinding extends ViewDataBinding {
    public final HeadTitleLinearView headTitle;
    public final LinearLayout main;
    public final RemainTimeView rtvTime;
    public final RecyclerView rvTime;

    protected ActivityTimeDetailBinding(Object obj, View view2, int i, HeadTitleLinearView headTitleLinearView, LinearLayout linearLayout, RemainTimeView remainTimeView, RecyclerView recyclerView) {
        super(obj, view2, i);
        this.headTitle = headTitleLinearView;
        this.main = linearLayout;
        this.rtvTime = remainTimeView;
        this.rvTime = recyclerView;
    }

    public static ActivityTimeDetailBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityTimeDetailBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityTimeDetailBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.activity_time_detail, viewGroup, z, obj);
    }

    public static ActivityTimeDetailBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityTimeDetailBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityTimeDetailBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.activity_time_detail, null, false, obj);
    }

    public static ActivityTimeDetailBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityTimeDetailBinding bind(View view2, Object obj) {
        return (ActivityTimeDetailBinding) bind(obj, view2, C1106R.layout.activity_time_detail);
    }
}
