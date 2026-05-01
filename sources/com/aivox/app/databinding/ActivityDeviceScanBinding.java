package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.common_ui.HeadTitleLinearView;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityDeviceScanBinding extends ViewDataBinding {
    public final ImageView ivLoading;
    public final LinearLayout llBtmNotify;
    public final LinearLayout llConnectDevice;
    public final LinearLayout llConnectFail;
    public final RecyclerView rcvDevices;
    public final RecyclerView rcvMoreDevices;
    public final HeadTitleLinearView titleView;
    public final TextView tvConnect;
    public final TextView tvEarphoneSteps;
    public final TextView tvMoreDevices;
    public final TextView tvMsg;
    public final TextView tvRegisterDevice;
    public final TextView tvSubTitle;

    protected ActivityDeviceScanBinding(Object obj, View view2, int i, ImageView imageView, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, RecyclerView recyclerView, RecyclerView recyclerView2, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6) {
        super(obj, view2, i);
        this.ivLoading = imageView;
        this.llBtmNotify = linearLayout;
        this.llConnectDevice = linearLayout2;
        this.llConnectFail = linearLayout3;
        this.rcvDevices = recyclerView;
        this.rcvMoreDevices = recyclerView2;
        this.titleView = headTitleLinearView;
        this.tvConnect = textView;
        this.tvEarphoneSteps = textView2;
        this.tvMoreDevices = textView3;
        this.tvMsg = textView4;
        this.tvRegisterDevice = textView5;
        this.tvSubTitle = textView6;
    }

    public static ActivityDeviceScanBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceScanBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityDeviceScanBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_device_scan, viewGroup, z, obj);
    }

    public static ActivityDeviceScanBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceScanBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityDeviceScanBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_device_scan, null, false, obj);
    }

    public static ActivityDeviceScanBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceScanBinding bind(View view2, Object obj) {
        return (ActivityDeviceScanBinding) bind(obj, view2, C0726R.layout.activity_device_scan);
    }
}
