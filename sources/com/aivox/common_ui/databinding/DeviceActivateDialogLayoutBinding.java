package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.DialogTitleView;

/* JADX INFO: loaded from: classes.dex */
public abstract class DeviceActivateDialogLayoutBinding extends ViewDataBinding {
    public final DialogTitleView dtvTitle;
    public final ImageView ivDeviceIcon;
    public final TextView tvActivateFailed;
    public final TextView tvActivateMsg;
    public final TextView tvActivateSuccess;

    protected DeviceActivateDialogLayoutBinding(Object obj, View view2, int i, DialogTitleView dialogTitleView, ImageView imageView, TextView textView, TextView textView2, TextView textView3) {
        super(obj, view2, i);
        this.dtvTitle = dialogTitleView;
        this.ivDeviceIcon = imageView;
        this.tvActivateFailed = textView;
        this.tvActivateMsg = textView2;
        this.tvActivateSuccess = textView3;
    }

    public static DeviceActivateDialogLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DeviceActivateDialogLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (DeviceActivateDialogLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.device_activate_dialog_layout, viewGroup, z, obj);
    }

    public static DeviceActivateDialogLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DeviceActivateDialogLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (DeviceActivateDialogLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.device_activate_dialog_layout, null, false, obj);
    }

    public static DeviceActivateDialogLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DeviceActivateDialogLayoutBinding bind(View view2, Object obj) {
        return (DeviceActivateDialogLayoutBinding) bind(obj, view2, C1034R.layout.device_activate_dialog_layout);
    }
}
