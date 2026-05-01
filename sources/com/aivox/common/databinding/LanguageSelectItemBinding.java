package com.aivox.common.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common.C0958R;
import com.aivox.common.model.LanguageSelectBean;

/* JADX INFO: loaded from: classes.dex */
public abstract class LanguageSelectItemBinding extends ViewDataBinding {
    public final ImageView ivLangSelected;

    @Bindable
    protected LanguageSelectBean mModel;
    public final TextView tvLang;

    public abstract void setModel(LanguageSelectBean languageSelectBean);

    protected LanguageSelectItemBinding(Object obj, View view2, int i, ImageView imageView, TextView textView) {
        super(obj, view2, i);
        this.ivLangSelected = imageView;
        this.tvLang = textView;
    }

    public LanguageSelectBean getModel() {
        return this.mModel;
    }

    public static LanguageSelectItemBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LanguageSelectItemBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (LanguageSelectItemBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.language_select_item, viewGroup, z, obj);
    }

    public static LanguageSelectItemBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LanguageSelectItemBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (LanguageSelectItemBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.language_select_item, null, false, obj);
    }

    public static LanguageSelectItemBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LanguageSelectItemBinding bind(View view2, Object obj) {
        return (LanguageSelectItemBinding) bind(obj, view2, C0958R.layout.language_select_item);
    }
}
