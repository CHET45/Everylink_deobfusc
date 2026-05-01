package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public abstract class SettingTileLayoutBinding extends ViewDataBinding {
    public final ConstraintLayout clContent;
    public final ConstraintLayout divider;
    public final ImageView ivMsgDot;
    public final ImageView ivRightIcon;
    public final ImageView ivTitleDot;
    public final ImageView ivTitleIcon;
    public final ImageView ivTitleLeading;
    public final LinearLayout llContainer;
    public final SwitchCompat switchRight;
    public final TextView tvMsg;
    public final TextView tvSubTitle;
    public final TextView tvTitle;

    protected SettingTileLayoutBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, LinearLayout linearLayout, SwitchCompat switchCompat, TextView textView, TextView textView2, TextView textView3) {
        super(obj, view2, i);
        this.clContent = constraintLayout;
        this.divider = constraintLayout2;
        this.ivMsgDot = imageView;
        this.ivRightIcon = imageView2;
        this.ivTitleDot = imageView3;
        this.ivTitleIcon = imageView4;
        this.ivTitleLeading = imageView5;
        this.llContainer = linearLayout;
        this.switchRight = switchCompat;
        this.tvMsg = textView;
        this.tvSubTitle = textView2;
        this.tvTitle = textView3;
    }

    public static SettingTileLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static SettingTileLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (SettingTileLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.setting_tile_layout, viewGroup, z, obj);
    }

    public static SettingTileLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static SettingTileLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (SettingTileLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.setting_tile_layout, null, false, obj);
    }

    public static SettingTileLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static SettingTileLayoutBinding bind(View view2, Object obj) {
        return (SettingTileLayoutBinding) bind(obj, view2, C1034R.layout.setting_tile_layout);
    }
}
