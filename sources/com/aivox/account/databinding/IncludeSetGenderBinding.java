package com.aivox.account.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.account.C0707R;

/* JADX INFO: loaded from: classes.dex */
public abstract class IncludeSetGenderBinding extends ViewDataBinding {
    public final TextView tvClose;
    public final TextView tvFemale;
    public final TextView tvMale;
    public final TextView tvTitle;

    protected IncludeSetGenderBinding(Object obj, View view2, int i, TextView textView, TextView textView2, TextView textView3, TextView textView4) {
        super(obj, view2, i);
        this.tvClose = textView;
        this.tvFemale = textView2;
        this.tvMale = textView3;
        this.tvTitle = textView4;
    }

    public static IncludeSetGenderBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeSetGenderBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (IncludeSetGenderBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.include_set_gender, viewGroup, z, obj);
    }

    public static IncludeSetGenderBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeSetGenderBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (IncludeSetGenderBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.include_set_gender, null, false, obj);
    }

    public static IncludeSetGenderBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeSetGenderBinding bind(View view2, Object obj) {
        return (IncludeSetGenderBinding) bind(obj, view2, C0707R.layout.include_set_gender);
    }
}
