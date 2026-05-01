package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public abstract class VipFeatureViewLayoutBinding extends ViewDataBinding {
    public final TextView tvFeature1;
    public final TextView tvFeature2;
    public final TextView tvFeature3;
    public final TextView tvFeature4;
    public final TextView tvFeature5;
    public final TextView tvFeature6;

    protected VipFeatureViewLayoutBinding(Object obj, View view2, int i, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6) {
        super(obj, view2, i);
        this.tvFeature1 = textView;
        this.tvFeature2 = textView2;
        this.tvFeature3 = textView3;
        this.tvFeature4 = textView4;
        this.tvFeature5 = textView5;
        this.tvFeature6 = textView6;
    }

    public static VipFeatureViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static VipFeatureViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (VipFeatureViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.vip_feature_view_layout, viewGroup, z, obj);
    }

    public static VipFeatureViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static VipFeatureViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (VipFeatureViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.vip_feature_view_layout, null, false, obj);
    }

    public static VipFeatureViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static VipFeatureViewLayoutBinding bind(View view2, Object obj) {
        return (VipFeatureViewLayoutBinding) bind(obj, view2, C1034R.layout.vip_feature_view_layout);
    }
}
