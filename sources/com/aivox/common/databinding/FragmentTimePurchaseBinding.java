package com.aivox.common.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common.C0958R;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public abstract class FragmentTimePurchaseBinding extends ViewDataBinding {
    public final LoadingButton btnDoCancel;
    public final LoadingButton btnDoNotCancel;
    public final LoadingButton btnPayNow;
    public final ImageView ivClose;
    public final LinearLayout llCancelSub;
    public final LinearLayout llTimePack;
    public final TextView tvAgreement;
    public final TextView tvMsg;
    public final TextView tvTimePack;
    public final TextView tvTitle;
    public final TextView tvValidUntil;

    protected FragmentTimePurchaseBinding(Object obj, View view2, int i, LoadingButton loadingButton, LoadingButton loadingButton2, LoadingButton loadingButton3, ImageView imageView, LinearLayout linearLayout, LinearLayout linearLayout2, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5) {
        super(obj, view2, i);
        this.btnDoCancel = loadingButton;
        this.btnDoNotCancel = loadingButton2;
        this.btnPayNow = loadingButton3;
        this.ivClose = imageView;
        this.llCancelSub = linearLayout;
        this.llTimePack = linearLayout2;
        this.tvAgreement = textView;
        this.tvMsg = textView2;
        this.tvTimePack = textView3;
        this.tvTitle = textView4;
        this.tvValidUntil = textView5;
    }

    public static FragmentTimePurchaseBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentTimePurchaseBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FragmentTimePurchaseBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.fragment_time_purchase, viewGroup, z, obj);
    }

    public static FragmentTimePurchaseBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentTimePurchaseBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FragmentTimePurchaseBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.fragment_time_purchase, null, false, obj);
    }

    public static FragmentTimePurchaseBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentTimePurchaseBinding bind(View view2, Object obj) {
        return (FragmentTimePurchaseBinding) bind(obj, view2, C0958R.layout.fragment_time_purchase);
    }
}
