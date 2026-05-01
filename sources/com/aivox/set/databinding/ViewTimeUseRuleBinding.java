package com.aivox.set.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.set.C1106R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ViewTimeUseRuleBinding extends ViewDataBinding {
    public final ImageView ivClose;
    public final TextView tvRuleDetail;
    public final TextView tvUseRule;

    protected ViewTimeUseRuleBinding(Object obj, View view2, int i, ImageView imageView, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.ivClose = imageView;
        this.tvRuleDetail = textView;
        this.tvUseRule = textView2;
    }

    public static ViewTimeUseRuleBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewTimeUseRuleBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ViewTimeUseRuleBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.view_time_use_rule, viewGroup, z, obj);
    }

    public static ViewTimeUseRuleBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewTimeUseRuleBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ViewTimeUseRuleBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.view_time_use_rule, null, false, obj);
    }

    public static ViewTimeUseRuleBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewTimeUseRuleBinding bind(View view2, Object obj) {
        return (ViewTimeUseRuleBinding) bind(obj, view2, C1106R.layout.view_time_use_rule);
    }
}
