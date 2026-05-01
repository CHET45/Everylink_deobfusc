package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.ViewPagerFix;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityMyPhotoBrowseBinding extends ViewDataBinding {
    public final ConstraintLayout container;
    public final GridLayout glFunction;
    public final TextView indicator;
    public final ImageView ivFavorite;
    public final LinearLayout llDelete;
    public final LinearLayout llFavorite;
    public final LinearLayout llSave;
    public final HeadTitleLinearView titleView;
    public final ViewPagerFix viewPager;

    protected ActivityMyPhotoBrowseBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, GridLayout gridLayout, TextView textView, ImageView imageView, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, HeadTitleLinearView headTitleLinearView, ViewPagerFix viewPagerFix) {
        super(obj, view2, i);
        this.container = constraintLayout;
        this.glFunction = gridLayout;
        this.indicator = textView;
        this.ivFavorite = imageView;
        this.llDelete = linearLayout;
        this.llFavorite = linearLayout2;
        this.llSave = linearLayout3;
        this.titleView = headTitleLinearView;
        this.viewPager = viewPagerFix;
    }

    public static ActivityMyPhotoBrowseBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityMyPhotoBrowseBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityMyPhotoBrowseBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_my_photo_browse, viewGroup, z, obj);
    }

    public static ActivityMyPhotoBrowseBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityMyPhotoBrowseBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityMyPhotoBrowseBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_my_photo_browse, null, false, obj);
    }

    public static ActivityMyPhotoBrowseBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityMyPhotoBrowseBinding bind(View view2, Object obj) {
        return (ActivityMyPhotoBrowseBinding) bind(obj, view2, C0726R.layout.activity_my_photo_browse);
    }
}
