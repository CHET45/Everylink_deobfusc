package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public abstract class RemainTimeItemViewLayoutBinding extends ViewDataBinding {
    public final ConstraintLayout clContent;
    public final TextView tvTimeExpired;
    public final TextView tvTimeRemain;
    public final TextView tvTimeType;
    public final View viewLabel;

    protected RemainTimeItemViewLayoutBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, TextView textView, TextView textView2, TextView textView3, View view3) {
        super(obj, view2, i);
        this.clContent = constraintLayout;
        this.tvTimeExpired = textView;
        this.tvTimeRemain = textView2;
        this.tvTimeType = textView3;
        this.viewLabel = view3;
    }

    public static RemainTimeItemViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static RemainTimeItemViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (RemainTimeItemViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.remain_time_item_view_layout, viewGroup, z, obj);
    }

    public static RemainTimeItemViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static RemainTimeItemViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (RemainTimeItemViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.remain_time_item_view_layout, null, false, obj);
    }

    public static RemainTimeItemViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static RemainTimeItemViewLayoutBinding bind(View view2, Object obj) {
        return (RemainTimeItemViewLayoutBinding) bind(obj, view2, C1034R.layout.remain_time_item_view_layout);
    }
}
