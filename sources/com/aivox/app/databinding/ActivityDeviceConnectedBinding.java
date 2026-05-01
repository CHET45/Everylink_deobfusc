package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common_ui.EarphonePowerView;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.LoadingButton;
import com.aivox.common_ui.SettingTileView;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityDeviceConnectedBinding extends ViewDataBinding {
    public final LoadingButton btnDisconnect;
    public final LoadingButton btnUpdate;
    public final EarphonePowerView epLeft;
    public final EarphonePowerView epPack;
    public final EarphonePowerView epRight;
    public final ImageView ivEarphoneIcon;
    public final LinearLayout llEq;
    public final LinearLayout llGlassCurrent;
    public final LinearLayout llGlassNew;
    public final LinearLayout llInfo;
    public final LinearLayout llNc;
    public final LinearLayout llSettings;
    public final SettingTileView stvAntiShake;
    public final SettingTileView stvEq;
    public final SettingTileView stvFiles;
    public final SettingTileView stvFirmware;
    public final SettingTileView stvFirmwareNew;
    public final SettingTileView stvInstruction;
    public final SettingTileView stvVersion;
    public final SettingTileView stvWifi;
    public final SettingTileView stvWifiNew;
    public final HeadTitleLinearView titleView;
    public final TextView tvAvailableVersion;
    public final TextView tvMac;
    public final TextView tvNcNormal;
    public final TextView tvNcOpen;
    public final TextView tvNcTrans;

    protected ActivityDeviceConnectedBinding(Object obj, View view2, int i, LoadingButton loadingButton, LoadingButton loadingButton2, EarphonePowerView earphonePowerView, EarphonePowerView earphonePowerView2, EarphonePowerView earphonePowerView3, ImageView imageView, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, LinearLayout linearLayout5, LinearLayout linearLayout6, SettingTileView settingTileView, SettingTileView settingTileView2, SettingTileView settingTileView3, SettingTileView settingTileView4, SettingTileView settingTileView5, SettingTileView settingTileView6, SettingTileView settingTileView7, SettingTileView settingTileView8, SettingTileView settingTileView9, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5) {
        super(obj, view2, i);
        this.btnDisconnect = loadingButton;
        this.btnUpdate = loadingButton2;
        this.epLeft = earphonePowerView;
        this.epPack = earphonePowerView2;
        this.epRight = earphonePowerView3;
        this.ivEarphoneIcon = imageView;
        this.llEq = linearLayout;
        this.llGlassCurrent = linearLayout2;
        this.llGlassNew = linearLayout3;
        this.llInfo = linearLayout4;
        this.llNc = linearLayout5;
        this.llSettings = linearLayout6;
        this.stvAntiShake = settingTileView;
        this.stvEq = settingTileView2;
        this.stvFiles = settingTileView3;
        this.stvFirmware = settingTileView4;
        this.stvFirmwareNew = settingTileView5;
        this.stvInstruction = settingTileView6;
        this.stvVersion = settingTileView7;
        this.stvWifi = settingTileView8;
        this.stvWifiNew = settingTileView9;
        this.titleView = headTitleLinearView;
        this.tvAvailableVersion = textView;
        this.tvMac = textView2;
        this.tvNcNormal = textView3;
        this.tvNcOpen = textView4;
        this.tvNcTrans = textView5;
    }

    public static ActivityDeviceConnectedBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceConnectedBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityDeviceConnectedBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_device_connected, viewGroup, z, obj);
    }

    public static ActivityDeviceConnectedBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceConnectedBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityDeviceConnectedBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_device_connected, null, false, obj);
    }

    public static ActivityDeviceConnectedBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityDeviceConnectedBinding bind(View view2, Object obj) {
        return (ActivityDeviceConnectedBinding) bind(obj, view2, C0726R.layout.activity_device_connected);
    }
}
