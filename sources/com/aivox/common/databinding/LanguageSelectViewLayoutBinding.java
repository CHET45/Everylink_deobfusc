package com.aivox.common.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.common.C0958R;
import com.aivox.common_ui.DialogTitleView;
import com.aivox.common_ui.LangItemView;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public abstract class LanguageSelectViewLayoutBinding extends ViewDataBinding {
    public final LoadingButton btnContinue;
    public final ConstraintLayout clLangSwitch;
    public final DialogTitleView dtvTitle;
    public final ImageView ivArrow;
    public final LangItemView livFromRecent1;
    public final LangItemView livFromRecent2;
    public final LangItemView livToRecent1;
    public final LangItemView livToRecent2;
    public final LinearLayout llLangRecentFrom;
    public final LinearLayout llLangRecentTo;
    public final RecyclerView rvLangFrom;
    public final RecyclerView rvLangTo;
    public final TextView tvLangL;
    public final TextView tvLangR;
    public final TextView tvLanguageAll;
    public final TextView tvLanguageRecent;

    protected LanguageSelectViewLayoutBinding(Object obj, View view2, int i, LoadingButton loadingButton, ConstraintLayout constraintLayout, DialogTitleView dialogTitleView, ImageView imageView, LangItemView langItemView, LangItemView langItemView2, LangItemView langItemView3, LangItemView langItemView4, LinearLayout linearLayout, LinearLayout linearLayout2, RecyclerView recyclerView, RecyclerView recyclerView2, TextView textView, TextView textView2, TextView textView3, TextView textView4) {
        super(obj, view2, i);
        this.btnContinue = loadingButton;
        this.clLangSwitch = constraintLayout;
        this.dtvTitle = dialogTitleView;
        this.ivArrow = imageView;
        this.livFromRecent1 = langItemView;
        this.livFromRecent2 = langItemView2;
        this.livToRecent1 = langItemView3;
        this.livToRecent2 = langItemView4;
        this.llLangRecentFrom = linearLayout;
        this.llLangRecentTo = linearLayout2;
        this.rvLangFrom = recyclerView;
        this.rvLangTo = recyclerView2;
        this.tvLangL = textView;
        this.tvLangR = textView2;
        this.tvLanguageAll = textView3;
        this.tvLanguageRecent = textView4;
    }

    public static LanguageSelectViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LanguageSelectViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (LanguageSelectViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.language_select_view_layout, viewGroup, z, obj);
    }

    public static LanguageSelectViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LanguageSelectViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (LanguageSelectViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.language_select_view_layout, null, false, obj);
    }

    public static LanguageSelectViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LanguageSelectViewLayoutBinding bind(View view2, Object obj) {
        return (LanguageSelectViewLayoutBinding) bind(obj, view2, C0958R.layout.language_select_view_layout);
    }
}
