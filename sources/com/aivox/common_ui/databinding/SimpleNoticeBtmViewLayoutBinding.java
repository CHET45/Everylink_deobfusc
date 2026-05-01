package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public abstract class SimpleNoticeBtmViewLayoutBinding extends ViewDataBinding {
    public final LoadingButton btnContinue;
    public final TextView tvDialogMsg;
    public final TextView tvDialogTitle;

    protected SimpleNoticeBtmViewLayoutBinding(Object obj, View view2, int i, LoadingButton loadingButton, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.btnContinue = loadingButton;
        this.tvDialogMsg = textView;
        this.tvDialogTitle = textView2;
    }

    public static SimpleNoticeBtmViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static SimpleNoticeBtmViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (SimpleNoticeBtmViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.simple_notice_btm_view_layout, viewGroup, z, obj);
    }

    public static SimpleNoticeBtmViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static SimpleNoticeBtmViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (SimpleNoticeBtmViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.simple_notice_btm_view_layout, null, false, obj);
    }

    public static SimpleNoticeBtmViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static SimpleNoticeBtmViewLayoutBinding bind(View view2, Object obj) {
        return (SimpleNoticeBtmViewLayoutBinding) bind(obj, view2, C1034R.layout.simple_notice_btm_view_layout);
    }
}
