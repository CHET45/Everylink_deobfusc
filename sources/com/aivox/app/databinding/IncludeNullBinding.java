package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;

/* JADX INFO: loaded from: classes.dex */
public abstract class IncludeNullBinding extends ViewDataBinding {
    protected IncludeNullBinding(Object obj, View view2, int i) {
        super(obj, view2, i);
    }

    public static IncludeNullBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeNullBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (IncludeNullBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.include_null, viewGroup, z, obj);
    }

    public static IncludeNullBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeNullBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (IncludeNullBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.include_null, null, false, obj);
    }

    public static IncludeNullBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeNullBinding bind(View view2, Object obj) {
        return (IncludeNullBinding) bind(obj, view2, C0726R.layout.include_null);
    }
}
