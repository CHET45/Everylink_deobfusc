package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityDeviceUpdateBinding extends ViewDataBinding {
    public final LoadingButton btnFinish;
    public final ImageView ivIc;
    public final ProgressBar pbProgress;
    public final HeadTitleLinearView titleView;
    public final TextView tvNoti;
    public final TextView tvNotiDetail;

    protected ActivityDeviceUpdateBinding(Object obj, View view2, int i, LoadingButton loadingButton, ImageView imageView, ProgressBar progressBar, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.btnFinish = loadingButton;
        this.ivIc = imageView;
        this.pbProgress = progressBar;
        this.titleView = headTitleLinearView;
        this.tvNoti = textView;
        this.tvNotiDetail = textView2;
    }

    public static ActivityDeviceUpdateBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceUpdateBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityDeviceUpdateBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_device_update, viewGroup, z, obj);
    }

    public static ActivityDeviceUpdateBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceUpdateBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityDeviceUpdateBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_device_update, null, false, obj);
    }

    public static ActivityDeviceUpdateBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceUpdateBinding bind(View view2, Object obj) {
        return (ActivityDeviceUpdateBinding) bind(obj, view2, C0726R.layout.activity_device_update);
    }
}
