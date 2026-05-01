package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public abstract class LoadingButtonLayoutBinding extends ViewDataBinding {
    public final ImageView ivLoading;
    public final RelativeLayout rlRoot;
    public final TextView tvBtn;

    protected LoadingButtonLayoutBinding(Object obj, View view2, int i, ImageView imageView, RelativeLayout relativeLayout, TextView textView) {
        super(obj, view2, i);
        this.ivLoading = imageView;
        this.rlRoot = relativeLayout;
        this.tvBtn = textView;
    }

    public static LoadingButtonLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LoadingButtonLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (LoadingButtonLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.loading_button_layout, viewGroup, z, obj);
    }

    public static LoadingButtonLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LoadingButtonLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (LoadingButtonLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.loading_button_layout, null, false, obj);
    }

    public static LoadingButtonLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LoadingButtonLayoutBinding bind(View view2, Object obj) {
        return (LoadingButtonLayoutBinding) bind(obj, view2, C1034R.layout.loading_button_layout);
    }
}
