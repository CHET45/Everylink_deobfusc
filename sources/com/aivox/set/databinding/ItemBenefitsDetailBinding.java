package com.aivox.set.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common.model.SpaceDetailListBean;
import com.aivox.set.C1106R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemBenefitsDetailBinding extends ViewDataBinding {

    @Bindable
    protected SpaceDetailListBean.DetailBean mXmlmodel;
    public final TextView tv1;
    public final TextView tv3;
    public final TextView tv4;
    public final TextView tvExpired;

    public abstract void setXmlmodel(SpaceDetailListBean.DetailBean detailBean);

    protected ItemBenefitsDetailBinding(Object obj, View view2, int i, TextView textView, TextView textView2, TextView textView3, TextView textView4) {
        super(obj, view2, i);
        this.tv1 = textView;
        this.tv3 = textView2;
        this.tv4 = textView3;
        this.tvExpired = textView4;
    }

    public SpaceDetailListBean.DetailBean getXmlmodel() {
        return this.mXmlmodel;
    }

    public static ItemBenefitsDetailBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBenefitsDetailBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemBenefitsDetailBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.item_benefits_detail, viewGroup, z, obj);
    }

    public static ItemBenefitsDetailBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBenefitsDetailBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemBenefitsDetailBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.item_benefits_detail, null, false, obj);
    }

    public static ItemBenefitsDetailBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBenefitsDetailBinding bind(View view2, Object obj) {
        return (ItemBenefitsDetailBinding) bind(obj, view2, C1106R.layout.item_benefits_detail);
    }
}
