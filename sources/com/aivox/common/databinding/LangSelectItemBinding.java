package com.aivox.common.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common.C0958R;

/* JADX INFO: loaded from: classes.dex */
public abstract class LangSelectItemBinding extends ViewDataBinding {
    public final ImageView ivLangArrow;
    public final TextView tvLangFrom;
    public final TextView tvLangTo;

    protected LangSelectItemBinding(Object obj, View view2, int i, ImageView imageView, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.ivLangArrow = imageView;
        this.tvLangFrom = textView;
        this.tvLangTo = textView2;
    }

    public static LangSelectItemBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LangSelectItemBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (LangSelectItemBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.lang_select_item, viewGroup, z, obj);
    }

    public static LangSelectItemBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LangSelectItemBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (LangSelectItemBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.lang_select_item, null, false, obj);
    }

    public static LangSelectItemBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LangSelectItemBinding bind(View view2, Object obj) {
        return (LangSelectItemBinding) bind(obj, view2, C0958R.layout.lang_select_item);
    }
}
