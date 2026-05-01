package com.aivox.set.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.common.model.PricePackageList;
import com.aivox.set.C1105BR;
import com.aivox.set.C1106R;

/* JADX INFO: loaded from: classes.dex */
public class ItemVipPackageBuyBindingImpl extends ItemVipPackageBuyBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C1106R.id.f321cl, 4);
        sparseIntArray.put(C1106R.id.tv_tag, 5);
    }

    public ItemVipPackageBuyBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 6, sIncludes, sViewsWithIds));
    }

    private ItemVipPackageBuyBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[4], (TextView) objArr[1], (TextView) objArr[2], (TextView) objArr[3], (TextView) objArr[5]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvName.setTag(null);
        this.tvPrice.setTag(null);
        this.tvStandardPrice.setTag(null);
        setRootTag(view2);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.mDirtyFlags != 0;
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, Object obj) {
        if (C1105BR.xmlmodel != i) {
            return false;
        }
        setXmlmodel((PricePackageList) obj);
        return true;
    }

    @Override // com.aivox.set.databinding.ItemVipPackageBuyBinding
    public void setXmlmodel(PricePackageList pricePackageList) {
        this.mXmlmodel = pricePackageList;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(C1105BR.xmlmodel);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        String withoutZeroPrice;
        String productName;
        Double price;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        PricePackageList pricePackageList = this.mXmlmodel;
        long j2 = j & 3;
        String str2 = null;
        Double originalPrice = null;
        if (j2 != 0) {
            if (pricePackageList != null) {
                originalPrice = pricePackageList.getOriginalPrice();
                productName = pricePackageList.getProductName();
                price = pricePackageList.getPrice();
            } else {
                productName = null;
                price = null;
            }
            double dSafeUnbox = ViewDataBinding.safeUnbox(originalPrice);
            double dSafeUnbox2 = ViewDataBinding.safeUnbox(price);
            String withoutZeroPrice2 = BaseStringUtil.getWithoutZeroPrice(dSafeUnbox);
            withoutZeroPrice = BaseStringUtil.getWithoutZeroPrice(dSafeUnbox2);
            String str3 = productName;
            str = withoutZeroPrice2;
            str2 = str3;
        } else {
            str = null;
            withoutZeroPrice = null;
        }
        if (j2 != 0) {
            TextViewBindingAdapter.setText(this.tvName, str2);
            TextViewBindingAdapter.setText(this.tvPrice, withoutZeroPrice);
            TextViewBindingAdapter.setText(this.tvStandardPrice, str);
        }
    }
}
