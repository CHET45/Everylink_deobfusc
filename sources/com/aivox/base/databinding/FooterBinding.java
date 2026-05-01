package com.aivox.base.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.base.C0874R;

/* JADX INFO: loaded from: classes.dex */
public abstract class FooterBinding extends ViewDataBinding {
    public final LinearLayout loadLayout;
    public final TextView moreDataMsg;

    protected FooterBinding(Object obj, View view2, int i, LinearLayout linearLayout, TextView textView) {
        super(obj, view2, i);
        this.loadLayout = linearLayout;
        this.moreDataMsg = textView;
    }

    public static FooterBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FooterBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FooterBinding) ViewDataBinding.inflateInternal(layoutInflater, C0874R.layout.footer, viewGroup, z, obj);
    }

    public static FooterBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FooterBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FooterBinding) ViewDataBinding.inflateInternal(layoutInflater, C0874R.layout.footer, null, false, obj);
    }

    public static FooterBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FooterBinding bind(View view2, Object obj) {
        return (FooterBinding) bind(obj, view2, C0874R.layout.footer);
    }
}
