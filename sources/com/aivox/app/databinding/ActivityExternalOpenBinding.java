package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common_ui.HeadTitleLinearView;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityExternalOpenBinding extends ViewDataBinding {
    public final ImageView ivIcon;
    public final HeadTitleLinearView titleView;
    public final TextView tvAudioSize;
    public final TextView tvAudioTime;
    public final TextView tvImport;
    public final TextView tvName;

    protected ActivityExternalOpenBinding(Object obj, View view2, int i, ImageView imageView, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2, TextView textView3, TextView textView4) {
        super(obj, view2, i);
        this.ivIcon = imageView;
        this.titleView = headTitleLinearView;
        this.tvAudioSize = textView;
        this.tvAudioTime = textView2;
        this.tvImport = textView3;
        this.tvName = textView4;
    }

    public static ActivityExternalOpenBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityExternalOpenBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityExternalOpenBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_external_open, viewGroup, z, obj);
    }

    public static ActivityExternalOpenBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityExternalOpenBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityExternalOpenBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_external_open, null, false, obj);
    }

    public static ActivityExternalOpenBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityExternalOpenBinding bind(View view2, Object obj) {
        return (ActivityExternalOpenBinding) bind(obj, view2, C0726R.layout.activity_external_open);
    }
}
