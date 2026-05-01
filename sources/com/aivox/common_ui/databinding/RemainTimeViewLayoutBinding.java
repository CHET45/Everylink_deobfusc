package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public abstract class RemainTimeViewLayoutBinding extends ViewDataBinding {
    public final TextView tvRemainTime;
    public final View viewTimeOther;
    public final View viewTimePurchase;
    public final View viewTimeVip;

    protected RemainTimeViewLayoutBinding(Object obj, View view2, int i, TextView textView, View view3, View view4, View view5) {
        super(obj, view2, i);
        this.tvRemainTime = textView;
        this.viewTimeOther = view3;
        this.viewTimePurchase = view4;
        this.viewTimeVip = view5;
    }

    public static RemainTimeViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static RemainTimeViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (RemainTimeViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.remain_time_view_layout, viewGroup, z, obj);
    }

    public static RemainTimeViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static RemainTimeViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (RemainTimeViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.remain_time_view_layout, null, false, obj);
    }

    public static RemainTimeViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static RemainTimeViewLayoutBinding bind(View view2, Object obj) {
        return (RemainTimeViewLayoutBinding) bind(obj, view2, C1034R.layout.remain_time_view_layout);
    }
}
