package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common_ui.HeadTitleLinearView;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityFunctionGuideBinding extends ViewDataBinding {
    public final ImageView ivStep1;
    public final ImageView ivStep2;

    @Bindable
    protected Integer mType;
    public final HeadTitleLinearView titleView;
    public final TextView tvStep1;
    public final TextView tvStep1Msg;
    public final TextView tvStep2;
    public final TextView tvStep2Msg;

    public abstract void setType(Integer num);

    protected ActivityFunctionGuideBinding(Object obj, View view2, int i, ImageView imageView, ImageView imageView2, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2, TextView textView3, TextView textView4) {
        super(obj, view2, i);
        this.ivStep1 = imageView;
        this.ivStep2 = imageView2;
        this.titleView = headTitleLinearView;
        this.tvStep1 = textView;
        this.tvStep1Msg = textView2;
        this.tvStep2 = textView3;
        this.tvStep2Msg = textView4;
    }

    public Integer getType() {
        return this.mType;
    }

    public static ActivityFunctionGuideBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityFunctionGuideBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityFunctionGuideBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_function_guide, viewGroup, z, obj);
    }

    public static ActivityFunctionGuideBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityFunctionGuideBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityFunctionGuideBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_function_guide, null, false, obj);
    }

    public static ActivityFunctionGuideBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityFunctionGuideBinding bind(View view2, Object obj) {
        return (ActivityFunctionGuideBinding) bind(obj, view2, C0726R.layout.activity_function_guide);
    }
}
