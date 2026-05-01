package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public abstract class PlanItemLayoutBinding extends ViewDataBinding {
    public final ImageView ivItemIcon;
    public final TextView tvItemSubTitle;
    public final TextView tvItemTitleHolder;
    public final TextView tvItemTitleName;
    public final TextView tvItemTitleUnit;
    public final TextView tvItemTitleValue;

    protected PlanItemLayoutBinding(Object obj, View view2, int i, ImageView imageView, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5) {
        super(obj, view2, i);
        this.ivItemIcon = imageView;
        this.tvItemSubTitle = textView;
        this.tvItemTitleHolder = textView2;
        this.tvItemTitleName = textView3;
        this.tvItemTitleUnit = textView4;
        this.tvItemTitleValue = textView5;
    }

    public static PlanItemLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static PlanItemLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (PlanItemLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.plan_item_layout, viewGroup, z, obj);
    }

    public static PlanItemLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static PlanItemLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (PlanItemLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.plan_item_layout, null, false, obj);
    }

    public static PlanItemLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static PlanItemLayoutBinding bind(View view2, Object obj) {
        return (PlanItemLayoutBinding) bind(obj, view2, C1034R.layout.plan_item_layout);
    }
}
