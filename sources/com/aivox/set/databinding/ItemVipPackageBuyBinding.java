package com.aivox.set.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common.model.PricePackageList;
import com.aivox.set.C1106R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemVipPackageBuyBinding extends ViewDataBinding {

    /* JADX INFO: renamed from: cl */
    public final ConstraintLayout f322cl;

    @Bindable
    protected PricePackageList mXmlmodel;
    public final TextView tvName;
    public final TextView tvPrice;
    public final TextView tvStandardPrice;
    public final TextView tvTag;

    public abstract void setXmlmodel(PricePackageList pricePackageList);

    protected ItemVipPackageBuyBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, TextView textView, TextView textView2, TextView textView3, TextView textView4) {
        super(obj, view2, i);
        this.f322cl = constraintLayout;
        this.tvName = textView;
        this.tvPrice = textView2;
        this.tvStandardPrice = textView3;
        this.tvTag = textView4;
    }

    public PricePackageList getXmlmodel() {
        return this.mXmlmodel;
    }

    public static ItemVipPackageBuyBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemVipPackageBuyBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemVipPackageBuyBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.item_vip_package_buy, viewGroup, z, obj);
    }

    public static ItemVipPackageBuyBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemVipPackageBuyBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemVipPackageBuyBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.item_vip_package_buy, null, false, obj);
    }

    public static ItemVipPackageBuyBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemVipPackageBuyBinding bind(View view2, Object obj) {
        return (ItemVipPackageBuyBinding) bind(obj, view2, C1106R.layout.item_vip_package_buy);
    }
}
