package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemRecordImgTagBinding extends ViewDataBinding {
    public final ImageView ivImg;

    protected ItemRecordImgTagBinding(Object obj, View view2, int i, ImageView imageView) {
        super(obj, view2, i);
        this.ivImg = imageView;
    }

    public static ItemRecordImgTagBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRecordImgTagBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemRecordImgTagBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_record_img_tag, viewGroup, z, obj);
    }

    public static ItemRecordImgTagBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRecordImgTagBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemRecordImgTagBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_record_img_tag, null, false, obj);
    }

    public static ItemRecordImgTagBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRecordImgTagBinding bind(View view2, Object obj) {
        return (ItemRecordImgTagBinding) bind(obj, view2, C0726R.layout.item_record_img_tag);
    }
}
