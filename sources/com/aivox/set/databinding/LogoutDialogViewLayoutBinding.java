package com.aivox.set.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.set.C1106R;

/* JADX INFO: loaded from: classes.dex */
public abstract class LogoutDialogViewLayoutBinding extends ViewDataBinding {
    public final ConstraintLayout cl1;
    public final TextView tvCancel;
    public final TextView tvDevice;
    public final TextView tvPhone;

    protected LogoutDialogViewLayoutBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, TextView textView, TextView textView2, TextView textView3) {
        super(obj, view2, i);
        this.cl1 = constraintLayout;
        this.tvCancel = textView;
        this.tvDevice = textView2;
        this.tvPhone = textView3;
    }

    public static LogoutDialogViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LogoutDialogViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (LogoutDialogViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.logout_dialog_view_layout, viewGroup, z, obj);
    }

    public static LogoutDialogViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LogoutDialogViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (LogoutDialogViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.logout_dialog_view_layout, null, false, obj);
    }

    public static LogoutDialogViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LogoutDialogViewLayoutBinding bind(View view2, Object obj) {
        return (LogoutDialogViewLayoutBinding) bind(obj, view2, C1106R.layout.logout_dialog_view_layout);
    }
}
