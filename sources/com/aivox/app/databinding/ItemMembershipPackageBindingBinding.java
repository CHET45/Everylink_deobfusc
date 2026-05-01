package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common.model.VipSelect;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemMembershipPackageBindingBinding extends ViewDataBinding {

    /* JADX INFO: renamed from: iv */
    public final ImageView f130iv;

    @Bindable
    protected VipSelect mXmlmodel;
    public final TextView tvContent;
    public final TextView tvPrice;

    public abstract void setXmlmodel(VipSelect vipSelect);

    protected ItemMembershipPackageBindingBinding(Object obj, View view2, int i, ImageView imageView, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.f130iv = imageView;
        this.tvContent = textView;
        this.tvPrice = textView2;
    }

    public VipSelect getXmlmodel() {
        return this.mXmlmodel;
    }

    public static ItemMembershipPackageBindingBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMembershipPackageBindingBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemMembershipPackageBindingBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_membership_package_binding, viewGroup, z, obj);
    }

    public static ItemMembershipPackageBindingBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMembershipPackageBindingBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemMembershipPackageBindingBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_membership_package_binding, null, false, obj);
    }

    public static ItemMembershipPackageBindingBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMembershipPackageBindingBinding bind(View view2, Object obj) {
        return (ItemMembershipPackageBindingBinding) bind(obj, view2, C0726R.layout.item_membership_package_binding);
    }
}
