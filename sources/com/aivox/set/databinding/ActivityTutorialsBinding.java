package com.aivox.set.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.SettingTileView;
import com.aivox.set.C1106R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityTutorialsBinding extends ViewDataBinding {
    public final Group gpGuide;
    public final ConstraintLayout main;
    public final SettingTileView stvBilingual;
    public final SettingTileView stvFiles;
    public final SettingTileView stvMain;
    public final HeadTitleLinearView titleView;
    public final View viewNext;
    public final View viewPrev;
    public final View viewSkip;
    public final ViewPager vpGuide;

    protected ActivityTutorialsBinding(Object obj, View view2, int i, Group group, ConstraintLayout constraintLayout, SettingTileView settingTileView, SettingTileView settingTileView2, SettingTileView settingTileView3, HeadTitleLinearView headTitleLinearView, View view3, View view4, View view5, ViewPager viewPager) {
        super(obj, view2, i);
        this.gpGuide = group;
        this.main = constraintLayout;
        this.stvBilingual = settingTileView;
        this.stvFiles = settingTileView2;
        this.stvMain = settingTileView3;
        this.titleView = headTitleLinearView;
        this.viewNext = view3;
        this.viewPrev = view4;
        this.viewSkip = view5;
        this.vpGuide = viewPager;
    }

    public static ActivityTutorialsBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityTutorialsBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityTutorialsBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.activity_tutorials, viewGroup, z, obj);
    }

    public static ActivityTutorialsBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityTutorialsBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityTutorialsBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.activity_tutorials, null, false, obj);
    }

    public static ActivityTutorialsBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityTutorialsBinding bind(View view2, Object obj) {
        return (ActivityTutorialsBinding) bind(obj, view2, C1106R.layout.activity_tutorials);
    }
}
