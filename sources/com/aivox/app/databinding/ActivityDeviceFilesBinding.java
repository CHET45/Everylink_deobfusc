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
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityDeviceFilesBinding extends ViewDataBinding {
    public final LoadingButton btnConnect;
    public final ImageView ivCloseX;
    public final LinearLayout llWifiInfo;
    public final LinearLayout main;
    public final RecyclerView rvDeviceFiles;
    public final HeadTitleLinearView titleView;
    public final TextView tvWifiName;
    public final TextView tvWifiNotice;
    public final TextView tvWifiPwd;

    protected ActivityDeviceFilesBinding(Object obj, View view2, int i, LoadingButton loadingButton, ImageView imageView, LinearLayout linearLayout, LinearLayout linearLayout2, RecyclerView recyclerView, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2, TextView textView3) {
        super(obj, view2, i);
        this.btnConnect = loadingButton;
        this.ivCloseX = imageView;
        this.llWifiInfo = linearLayout;
        this.main = linearLayout2;
        this.rvDeviceFiles = recyclerView;
        this.titleView = headTitleLinearView;
        this.tvWifiName = textView;
        this.tvWifiNotice = textView2;
        this.tvWifiPwd = textView3;
    }

    public static ActivityDeviceFilesBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceFilesBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityDeviceFilesBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_device_files, viewGroup, z, obj);
    }

    public static ActivityDeviceFilesBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceFilesBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityDeviceFilesBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_device_files, null, false, obj);
    }

    public static ActivityDeviceFilesBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceFilesBinding bind(View view2, Object obj) {
        return (ActivityDeviceFilesBinding) bind(obj, view2, C0726R.layout.activity_device_files);
    }
}
