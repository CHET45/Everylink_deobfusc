package com.aivox.set.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.SettingTileView;
import com.aivox.set.C1106R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityMembershipBinding extends ViewDataBinding {

    @Bindable
    protected OnViewClickListener mClickListener;
    public final SettingTileView stvHistory;
    public final SettingTileView stvUpgrade;
    public final HeadTitleLinearView titleView;

    public abstract void setClickListener(OnViewClickListener onViewClickListener);

    protected ActivityMembershipBinding(Object obj, View view2, int i, SettingTileView settingTileView, SettingTileView settingTileView2, HeadTitleLinearView headTitleLinearView) {
        super(obj, view2, i);
        this.stvHistory = settingTileView;
        this.stvUpgrade = settingTileView2;
        this.titleView = headTitleLinearView;
    }

    public OnViewClickListener getClickListener() {
        return this.mClickListener;
    }

    public static ActivityMembershipBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityMembershipBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityMembershipBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.activity_membership, viewGroup, z, obj);
    }

    public static ActivityMembershipBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityMembershipBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityMembershipBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.activity_membership, null, false, obj);
    }

    public static ActivityMembershipBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityMembershipBinding bind(View view2, Object obj) {
        return (ActivityMembershipBinding) bind(obj, view2, C1106R.layout.activity_membership);
    }
}
