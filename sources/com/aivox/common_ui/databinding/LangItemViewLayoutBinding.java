package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public abstract class LangItemViewLayoutBinding extends ViewDataBinding {
    public final ImageView ivLangChecked;
    public final ImageView ivLangNotice;
    public final LinearLayout llLangContent;
    public final TextView tvLangName;
    public final TextView tvLangPro;

    protected LangItemViewLayoutBinding(Object obj, View view2, int i, ImageView imageView, ImageView imageView2, LinearLayout linearLayout, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.ivLangChecked = imageView;
        this.ivLangNotice = imageView2;
        this.llLangContent = linearLayout;
        this.tvLangName = textView;
        this.tvLangPro = textView2;
    }

    public static LangItemViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LangItemViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (LangItemViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.lang_item_view_layout, viewGroup, z, obj);
    }

    public static LangItemViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LangItemViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (LangItemViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.lang_item_view_layout, null, false, obj);
    }

    public static LangItemViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LangItemViewLayoutBinding bind(View view2, Object obj) {
        return (LangItemViewLayoutBinding) bind(obj, view2, C1034R.layout.lang_item_view_layout);
    }
}
