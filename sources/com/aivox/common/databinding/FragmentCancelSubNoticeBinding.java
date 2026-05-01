package com.aivox.common.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common.C0958R;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public abstract class FragmentCancelSubNoticeBinding extends ViewDataBinding {
    public final LoadingButton btnGoHome;
    public final ImageView ivClose;
    public final TextView tvCancelApple;
    public final TextView tvCancelGoogle;
    public final TextView tvCancelOnApple;
    public final TextView tvCancelOnGoogle;
    public final TextView tvMsg;
    public final TextView tvTitle;

    protected FragmentCancelSubNoticeBinding(Object obj, View view2, int i, LoadingButton loadingButton, ImageView imageView, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6) {
        super(obj, view2, i);
        this.btnGoHome = loadingButton;
        this.ivClose = imageView;
        this.tvCancelApple = textView;
        this.tvCancelGoogle = textView2;
        this.tvCancelOnApple = textView3;
        this.tvCancelOnGoogle = textView4;
        this.tvMsg = textView5;
        this.tvTitle = textView6;
    }

    public static FragmentCancelSubNoticeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentCancelSubNoticeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FragmentCancelSubNoticeBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.fragment_cancel_sub_notice, viewGroup, z, obj);
    }

    public static FragmentCancelSubNoticeBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentCancelSubNoticeBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FragmentCancelSubNoticeBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.fragment_cancel_sub_notice, null, false, obj);
    }

    public static FragmentCancelSubNoticeBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentCancelSubNoticeBinding bind(View view2, Object obj) {
        return (FragmentCancelSubNoticeBinding) bind(obj, view2, C0958R.layout.fragment_cancel_sub_notice);
    }
}
