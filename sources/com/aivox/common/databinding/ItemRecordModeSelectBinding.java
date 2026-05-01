package com.aivox.common.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common.C0958R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemRecordModeSelectBinding extends ViewDataBinding {
    public final ConstraintLayout clMode;
    public final ImageView ivMode;
    public final TextView tvModeDesc;
    public final TextView tvModeName;

    protected ItemRecordModeSelectBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ImageView imageView, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.clMode = constraintLayout;
        this.ivMode = imageView;
        this.tvModeDesc = textView;
        this.tvModeName = textView2;
    }

    public static ItemRecordModeSelectBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRecordModeSelectBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemRecordModeSelectBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.item_record_mode_select, viewGroup, z, obj);
    }

    public static ItemRecordModeSelectBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRecordModeSelectBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemRecordModeSelectBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.item_record_mode_select, null, false, obj);
    }

    public static ItemRecordModeSelectBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRecordModeSelectBinding bind(View view2, Object obj) {
        return (ItemRecordModeSelectBinding) bind(obj, view2, C0958R.layout.item_record_mode_select);
    }
}
