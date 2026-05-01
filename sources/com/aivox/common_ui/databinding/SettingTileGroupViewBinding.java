package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public abstract class SettingTileGroupViewBinding extends ViewDataBinding {
    public final TextView tvTitle;

    protected SettingTileGroupViewBinding(Object obj, View view2, int i, TextView textView) {
        super(obj, view2, i);
        this.tvTitle = textView;
    }

    public static SettingTileGroupViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static SettingTileGroupViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (SettingTileGroupViewBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.setting_tile_group_view, viewGroup, z, obj);
    }

    public static SettingTileGroupViewBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static SettingTileGroupViewBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (SettingTileGroupViewBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.setting_tile_group_view, null, false, obj);
    }

    public static SettingTileGroupViewBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static SettingTileGroupViewBinding bind(View view2, Object obj) {
        return (SettingTileGroupViewBinding) bind(obj, view2, C1034R.layout.setting_tile_group_view);
    }
}
