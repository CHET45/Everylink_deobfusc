package com.aivox.base.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.base.C0874R;

/* JADX INFO: loaded from: classes.dex */
public abstract class IncludeNullViewBinding extends ViewDataBinding {

    /* JADX INFO: renamed from: iv */
    public final ImageView f179iv;
    public final LinearLayout nullView;

    /* JADX INFO: renamed from: tv */
    public final TextView f180tv;

    protected IncludeNullViewBinding(Object obj, View view2, int i, ImageView imageView, LinearLayout linearLayout, TextView textView) {
        super(obj, view2, i);
        this.f179iv = imageView;
        this.nullView = linearLayout;
        this.f180tv = textView;
    }

    public static IncludeNullViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeNullViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (IncludeNullViewBinding) ViewDataBinding.inflateInternal(layoutInflater, C0874R.layout.include_null_view, viewGroup, z, obj);
    }

    public static IncludeNullViewBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeNullViewBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (IncludeNullViewBinding) ViewDataBinding.inflateInternal(layoutInflater, C0874R.layout.include_null_view, null, false, obj);
    }

    public static IncludeNullViewBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeNullViewBinding bind(View view2, Object obj) {
        return (IncludeNullViewBinding) bind(obj, view2, C0874R.layout.include_null_view);
    }
}
