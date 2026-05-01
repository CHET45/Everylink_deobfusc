package com.aivox.set.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.CustomTextView;
import com.aivox.set.C1106R;

/* JADX INFO: loaded from: classes.dex */
public abstract class TimeChargeNoticeViewLayoutBinding extends ViewDataBinding {
    public final ImageView ivIcon;
    public final CustomTextView tvContent;

    protected TimeChargeNoticeViewLayoutBinding(Object obj, View view2, int i, ImageView imageView, CustomTextView customTextView) {
        super(obj, view2, i);
        this.ivIcon = imageView;
        this.tvContent = customTextView;
    }

    public static TimeChargeNoticeViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static TimeChargeNoticeViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (TimeChargeNoticeViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.time_charge_notice_view_layout, viewGroup, z, obj);
    }

    public static TimeChargeNoticeViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static TimeChargeNoticeViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (TimeChargeNoticeViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.time_charge_notice_view_layout, null, false, obj);
    }

    public static TimeChargeNoticeViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static TimeChargeNoticeViewLayoutBinding bind(View view2, Object obj) {
        return (TimeChargeNoticeViewLayoutBinding) bind(obj, view2, C1106R.layout.time_charge_notice_view_layout);
    }
}
