package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemBleDeviceBinding extends ViewDataBinding {
    public final ImageView ivDeviceIcon;
    public final ImageView ivDeviceSignal;
    public final TextView tvDeviceDesc;
    public final TextView tvDeviceName;

    protected ItemBleDeviceBinding(Object obj, View view2, int i, ImageView imageView, ImageView imageView2, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.ivDeviceIcon = imageView;
        this.ivDeviceSignal = imageView2;
        this.tvDeviceDesc = textView;
        this.tvDeviceName = textView2;
    }

    public static ItemBleDeviceBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBleDeviceBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemBleDeviceBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_ble_device, viewGroup, z, obj);
    }

    public static ItemBleDeviceBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBleDeviceBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemBleDeviceBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_ble_device, null, false, obj);
    }

    public static ItemBleDeviceBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBleDeviceBinding bind(View view2, Object obj) {
        return (ItemBleDeviceBinding) bind(obj, view2, C0726R.layout.item_ble_device);
    }
}
