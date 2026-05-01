package com.aivox.account.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.account.C0707R;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.LoadingButton;
import com.aivox.common_ui.RoundedCornerBitmap;
import com.aivox.common_ui.SettingTileView;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityUserInfoBinding extends ViewDataBinding {
    public final LinearLayout clCenter;
    public final ConstraintLayout clHead;
    public final RoundedCornerBitmap ivHead;

    @Bindable
    protected OnViewClickListener mClickListener;
    public final SettingTileView stvBirthday;
    public final SettingTileView stvGender;
    public final SettingTileView stvNickname;
    public final SettingTileView stvPhone;
    public final SettingTileView stvPwd;
    public final HeadTitleLinearView titleView;
    public final TextView tvDeleteAccount;
    public final TextView tvHead;
    public final LoadingButton tvSignOut;

    public abstract void setClickListener(OnViewClickListener onViewClickListener);

    protected ActivityUserInfoBinding(Object obj, View view2, int i, LinearLayout linearLayout, ConstraintLayout constraintLayout, RoundedCornerBitmap roundedCornerBitmap, SettingTileView settingTileView, SettingTileView settingTileView2, SettingTileView settingTileView3, SettingTileView settingTileView4, SettingTileView settingTileView5, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2, LoadingButton loadingButton) {
        super(obj, view2, i);
        this.clCenter = linearLayout;
        this.clHead = constraintLayout;
        this.ivHead = roundedCornerBitmap;
        this.stvBirthday = settingTileView;
        this.stvGender = settingTileView2;
        this.stvNickname = settingTileView3;
        this.stvPhone = settingTileView4;
        this.stvPwd = settingTileView5;
        this.titleView = headTitleLinearView;
        this.tvDeleteAccount = textView;
        this.tvHead = textView2;
        this.tvSignOut = loadingButton;
    }

    public OnViewClickListener getClickListener() {
        return this.mClickListener;
    }

    public static ActivityUserInfoBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityUserInfoBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityUserInfoBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.activity_user_info, viewGroup, z, obj);
    }

    public static ActivityUserInfoBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityUserInfoBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityUserInfoBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.activity_user_info, null, false, obj);
    }

    public static ActivityUserInfoBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityUserInfoBinding bind(View view2, Object obj) {
        return (ActivityUserInfoBinding) bind(obj, view2, C0707R.layout.activity_user_info);
    }
}
