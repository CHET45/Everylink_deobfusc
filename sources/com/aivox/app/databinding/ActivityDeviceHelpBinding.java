package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityDeviceHelpBinding extends ViewDataBinding {
    protected ActivityDeviceHelpBinding(Object obj, View view2, int i) {
        super(obj, view2, i);
    }

    public static ActivityDeviceHelpBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceHelpBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityDeviceHelpBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_device_help, viewGroup, z, obj);
    }

    public static ActivityDeviceHelpBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceHelpBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityDeviceHelpBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_device_help, null, false, obj);
    }

    public static ActivityDeviceHelpBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceHelpBinding bind(View view2, Object obj) {
        return (ActivityDeviceHelpBinding) bind(obj, view2, C0726R.layout.activity_device_help);
    }
}
