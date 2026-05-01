package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public abstract class LangSwitchViewLayoutBinding extends ViewDataBinding {
    public final Group groupTranslate;
    public final ImageView ivSwitch;
    public final TextView tvLangFrom;
    public final TextView tvLangTo;
    public final TextView tvTranscribe;

    protected LangSwitchViewLayoutBinding(Object obj, View view2, int i, Group group, ImageView imageView, TextView textView, TextView textView2, TextView textView3) {
        super(obj, view2, i);
        this.groupTranslate = group;
        this.ivSwitch = imageView;
        this.tvLangFrom = textView;
        this.tvLangTo = textView2;
        this.tvTranscribe = textView3;
    }

    public static LangSwitchViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LangSwitchViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (LangSwitchViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.lang_switch_view_layout, viewGroup, z, obj);
    }

    public static LangSwitchViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LangSwitchViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (LangSwitchViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.lang_switch_view_layout, null, false, obj);
    }

    public static LangSwitchViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LangSwitchViewLayoutBinding bind(View view2, Object obj) {
        return (LangSwitchViewLayoutBinding) bind(obj, view2, C1034R.layout.lang_switch_view_layout);
    }
}
