package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.DialogTitleView;
import com.aivox.common_ui.LoadingButton;
import com.github.gzuliyujiang.wheelview.widget.WheelView;

/* JADX INFO: loaded from: classes.dex */
public abstract class DateScrollSelectViewLayoutBinding extends ViewDataBinding {
    public final LoadingButton btnContinue;
    public final DialogTitleView dtvTitle;
    public final LinearLayout llWheelContainer;
    public final WheelView wvDateDay;
    public final WheelView wvDateMonth;
    public final WheelView wvDateYear;

    protected DateScrollSelectViewLayoutBinding(Object obj, View view2, int i, LoadingButton loadingButton, DialogTitleView dialogTitleView, LinearLayout linearLayout, WheelView wheelView, WheelView wheelView2, WheelView wheelView3) {
        super(obj, view2, i);
        this.btnContinue = loadingButton;
        this.dtvTitle = dialogTitleView;
        this.llWheelContainer = linearLayout;
        this.wvDateDay = wheelView;
        this.wvDateMonth = wheelView2;
        this.wvDateYear = wheelView3;
    }

    public static DateScrollSelectViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DateScrollSelectViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (DateScrollSelectViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.date_scroll_select_view_layout, viewGroup, z, obj);
    }

    public static DateScrollSelectViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DateScrollSelectViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (DateScrollSelectViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.date_scroll_select_view_layout, null, false, obj);
    }

    public static DateScrollSelectViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DateScrollSelectViewLayoutBinding bind(View view2, Object obj) {
        return (DateScrollSelectViewLayoutBinding) bind(obj, view2, C1034R.layout.date_scroll_select_view_layout);
    }
}
