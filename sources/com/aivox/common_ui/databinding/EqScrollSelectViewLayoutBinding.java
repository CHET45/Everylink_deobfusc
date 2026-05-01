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
public abstract class EqScrollSelectViewLayoutBinding extends ViewDataBinding {
    public final LoadingButton btnContinue;
    public final DialogTitleView dtvTitle;
    public final LinearLayout llWheelContainer;
    public final WheelView wvEq;

    protected EqScrollSelectViewLayoutBinding(Object obj, View view2, int i, LoadingButton loadingButton, DialogTitleView dialogTitleView, LinearLayout linearLayout, WheelView wheelView) {
        super(obj, view2, i);
        this.btnContinue = loadingButton;
        this.dtvTitle = dialogTitleView;
        this.llWheelContainer = linearLayout;
        this.wvEq = wheelView;
    }

    public static EqScrollSelectViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static EqScrollSelectViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (EqScrollSelectViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.eq_scroll_select_view_layout, viewGroup, z, obj);
    }

    public static EqScrollSelectViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static EqScrollSelectViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (EqScrollSelectViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.eq_scroll_select_view_layout, null, false, obj);
    }

    public static EqScrollSelectViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static EqScrollSelectViewLayoutBinding bind(View view2, Object obj) {
        return (EqScrollSelectViewLayoutBinding) bind(obj, view2, C1034R.layout.eq_scroll_select_view_layout);
    }
}
