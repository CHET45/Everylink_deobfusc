package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public abstract class EarphonePowerBinding extends ViewDataBinding {
    public final ImageView ivPower;
    public final ConstraintLayout llContainer;
    public final TextView tvPower;

    protected EarphonePowerBinding(Object obj, View view2, int i, ImageView imageView, ConstraintLayout constraintLayout, TextView textView) {
        super(obj, view2, i);
        this.ivPower = imageView;
        this.llContainer = constraintLayout;
        this.tvPower = textView;
    }

    public static EarphonePowerBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static EarphonePowerBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (EarphonePowerBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.earphone_power, viewGroup, z, obj);
    }

    public static EarphonePowerBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static EarphonePowerBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (EarphonePowerBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.earphone_power, null, false, obj);
    }

    public static EarphonePowerBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static EarphonePowerBinding bind(View view2, Object obj) {
        return (EarphonePowerBinding) bind(obj, view2, C1034R.layout.earphone_power);
    }
}
