package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public abstract class DialogTitleViewLayoutBinding extends ViewDataBinding {
    public final ImageView ivClose;
    public final ImageView ivInfo;
    public final TextView tvTitle;

    protected DialogTitleViewLayoutBinding(Object obj, View view2, int i, ImageView imageView, ImageView imageView2, TextView textView) {
        super(obj, view2, i);
        this.ivClose = imageView;
        this.ivInfo = imageView2;
        this.tvTitle = textView;
    }

    public static DialogTitleViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTitleViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (DialogTitleViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.dialog_title_view_layout, viewGroup, z, obj);
    }

    public static DialogTitleViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTitleViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (DialogTitleViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.dialog_title_view_layout, null, false, obj);
    }

    public static DialogTitleViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTitleViewLayoutBinding bind(View view2, Object obj) {
        return (DialogTitleViewLayoutBinding) bind(obj, view2, C1034R.layout.dialog_title_view_layout);
    }
}
