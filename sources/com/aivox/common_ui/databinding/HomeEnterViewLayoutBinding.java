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
public abstract class HomeEnterViewLayoutBinding extends ViewDataBinding {
    public final ImageView ivEnterIcon;
    public final TextView tvEnterName;
    public final TextView tvEnterState;

    protected HomeEnterViewLayoutBinding(Object obj, View view2, int i, ImageView imageView, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.ivEnterIcon = imageView;
        this.tvEnterName = textView;
        this.tvEnterState = textView2;
    }

    public static HomeEnterViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static HomeEnterViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (HomeEnterViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.home_enter_view_layout, viewGroup, z, obj);
    }

    public static HomeEnterViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static HomeEnterViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (HomeEnterViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.home_enter_view_layout, null, false, obj);
    }

    public static HomeEnterViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static HomeEnterViewLayoutBinding bind(View view2, Object obj) {
        return (HomeEnterViewLayoutBinding) bind(obj, view2, C1034R.layout.home_enter_view_layout);
    }
}
