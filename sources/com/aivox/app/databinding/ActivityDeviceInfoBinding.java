package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.SettingTileView;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityDeviceInfoBinding extends ViewDataBinding {
    public final ImageView ivDeviceIcon;
    public final SettingTileView stvHowToPair;
    public final HeadTitleLinearView titleView;

    protected ActivityDeviceInfoBinding(Object obj, View view2, int i, ImageView imageView, SettingTileView settingTileView, HeadTitleLinearView headTitleLinearView) {
        super(obj, view2, i);
        this.ivDeviceIcon = imageView;
        this.stvHowToPair = settingTileView;
        this.titleView = headTitleLinearView;
    }

    public static ActivityDeviceInfoBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceInfoBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityDeviceInfoBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_device_info, viewGroup, z, obj);
    }

    public static ActivityDeviceInfoBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceInfoBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityDeviceInfoBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_device_info, null, false, obj);
    }

    public static ActivityDeviceInfoBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceInfoBinding bind(View view2, Object obj) {
        return (ActivityDeviceInfoBinding) bind(obj, view2, C0726R.layout.activity_device_info);
    }
}
