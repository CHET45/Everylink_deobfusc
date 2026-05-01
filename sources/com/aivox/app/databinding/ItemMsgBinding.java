package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common.model.Msg;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemMsgBinding extends ViewDataBinding {
    public final ConstraintLayout clCenter;
    public final ImageView ivMsgUnread;
    public final ConstraintLayout llDel;

    @Bindable
    protected Msg mXmlmodel;
    public final ImageView tvDel;
    public final TextView tvMsgTitle;
    public final TextView tvTime;

    public abstract void setXmlmodel(Msg msg);

    protected ItemMsgBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ImageView imageView, ConstraintLayout constraintLayout2, ImageView imageView2, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.clCenter = constraintLayout;
        this.ivMsgUnread = imageView;
        this.llDel = constraintLayout2;
        this.tvDel = imageView2;
        this.tvMsgTitle = textView;
        this.tvTime = textView2;
    }

    public Msg getXmlmodel() {
        return this.mXmlmodel;
    }

    public static ItemMsgBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMsgBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemMsgBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_msg, viewGroup, z, obj);
    }

    public static ItemMsgBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMsgBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemMsgBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_msg, null, false, obj);
    }

    public static ItemMsgBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMsgBinding bind(View view2, Object obj) {
        return (ItemMsgBinding) bind(obj, view2, C0726R.layout.item_msg);
    }
}
