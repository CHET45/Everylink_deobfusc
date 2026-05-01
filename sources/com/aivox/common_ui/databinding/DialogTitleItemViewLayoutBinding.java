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
public abstract class DialogTitleItemViewLayoutBinding extends ViewDataBinding {
    public final ImageView ivItemRightIcon;
    public final TextView tvItemLimit;
    public final TextView tvItemTitle;

    protected DialogTitleItemViewLayoutBinding(Object obj, View view2, int i, ImageView imageView, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.ivItemRightIcon = imageView;
        this.tvItemLimit = textView;
        this.tvItemTitle = textView2;
    }

    public static DialogTitleItemViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTitleItemViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (DialogTitleItemViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.dialog_title_item_view_layout, viewGroup, z, obj);
    }

    public static DialogTitleItemViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTitleItemViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (DialogTitleItemViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.dialog_title_item_view_layout, null, false, obj);
    }

    public static DialogTitleItemViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTitleItemViewLayoutBinding bind(View view2, Object obj) {
        return (DialogTitleItemViewLayoutBinding) bind(obj, view2, C1034R.layout.dialog_title_item_view_layout);
    }
}
