package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common.model.VipSelect;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemVipPriceBinding extends ViewDataBinding {

    @Bindable
    protected VipSelect mXmlmodel;
    public final TextView tvCommon1;

    public abstract void setXmlmodel(VipSelect vipSelect);

    protected ItemVipPriceBinding(Object obj, View view2, int i, TextView textView) {
        super(obj, view2, i);
        this.tvCommon1 = textView;
    }

    public VipSelect getXmlmodel() {
        return this.mXmlmodel;
    }

    public static ItemVipPriceBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemVipPriceBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemVipPriceBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_vip_price, viewGroup, z, obj);
    }

    public static ItemVipPriceBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemVipPriceBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemVipPriceBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_vip_price, null, false, obj);
    }

    public static ItemVipPriceBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemVipPriceBinding bind(View view2, Object obj) {
        return (ItemVipPriceBinding) bind(obj, view2, C0726R.layout.item_vip_price);
    }
}
