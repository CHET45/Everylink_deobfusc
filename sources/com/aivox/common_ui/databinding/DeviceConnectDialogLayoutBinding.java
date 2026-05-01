package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.DialogTitleView;
import com.aivox.common_ui.EarphonePowerView;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public abstract class DeviceConnectDialogLayoutBinding extends ViewDataBinding {
    public final LoadingButton btnConnect;
    public final DialogTitleView dtvTitle;
    public final EarphonePowerView epLeft;
    public final EarphonePowerView epPack;
    public final EarphonePowerView epRight;
    public final ImageView ivDeviceIcon;
    public final LinearLayout llBattery;
    public final TextView tvConnectFailed;
    public final TextView tvConnectSuccess;

    protected DeviceConnectDialogLayoutBinding(Object obj, View view2, int i, LoadingButton loadingButton, DialogTitleView dialogTitleView, EarphonePowerView earphonePowerView, EarphonePowerView earphonePowerView2, EarphonePowerView earphonePowerView3, ImageView imageView, LinearLayout linearLayout, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.btnConnect = loadingButton;
        this.dtvTitle = dialogTitleView;
        this.epLeft = earphonePowerView;
        this.epPack = earphonePowerView2;
        this.epRight = earphonePowerView3;
        this.ivDeviceIcon = imageView;
        this.llBattery = linearLayout;
        this.tvConnectFailed = textView;
        this.tvConnectSuccess = textView2;
    }

    public static DeviceConnectDialogLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DeviceConnectDialogLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (DeviceConnectDialogLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.device_connect_dialog_layout, viewGroup, z, obj);
    }

    public static DeviceConnectDialogLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DeviceConnectDialogLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (DeviceConnectDialogLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.device_connect_dialog_layout, null, false, obj);
    }

    public static DeviceConnectDialogLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DeviceConnectDialogLayoutBinding bind(View view2, Object obj) {
        return (DeviceConnectDialogLayoutBinding) bind(obj, view2, C1034R.layout.device_connect_dialog_layout);
    }
}
