package com.aivox.set.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.common.model.SpaceDetailListBean;
import com.aivox.set.C1105BR;

/* JADX INFO: loaded from: classes.dex */
public class ItemBenefitsDetailBindingImpl extends ItemBenefitsDetailBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    public ItemBenefitsDetailBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 5, sIncludes, sViewsWithIds));
    }

    private ItemBenefitsDetailBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (TextView) objArr[1], (TextView) objArr[3], (TextView) objArr[4], (TextView) objArr[2]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tv1.setTag(null);
        this.tv3.setTag(null);
        this.tv4.setTag(null);
        this.tvExpired.setTag(null);
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
        setXmlmodel((SpaceDetailListBean.DetailBean) obj);
        return true;
    }

    @Override // com.aivox.set.databinding.ItemBenefitsDetailBinding
    public void setXmlmodel(SpaceDetailListBean.DetailBean detailBean) {
        this.mXmlmodel = detailBean;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(C1105BR.xmlmodel);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String strMbFormat;
        String type;
        String expireAt;
        int i;
        boolean zIsExpired;
        int capacity;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        SpaceDetailListBean.DetailBean detailBean = this.mXmlmodel;
        long j2 = j & 3;
        String str = null;
        boolean z = false;
        if (j2 != 0) {
            if (detailBean != null) {
                capacity = detailBean.getCapacity();
                type = detailBean.getType();
                expireAt = detailBean.getExpireAt();
                zIsExpired = detailBean.isExpired();
            } else {
                type = null;
                expireAt = null;
                zIsExpired = false;
                capacity = 0;
            }
            if (j2 != 0) {
                j |= zIsExpired ? 32L : 16L;
            }
            strMbFormat = BaseStringUtil.MbFormat(capacity);
            boolean zIsEmpty = BaseStringUtil.isEmpty(expireAt);
            int i2 = zIsExpired ? 0 : 8;
            if ((j & 3) != 0) {
                j = zIsEmpty ? j | 8 : j | 4;
            }
            i = i2;
            z = zIsEmpty;
        } else {
            strMbFormat = null;
            type = null;
            expireAt = null;
            i = 0;
        }
        String strUtc2Local = (4 & j) != 0 ? DateUtil.utc2Local(expireAt, DateUtil.YYYYMMDD_expired) : null;
        long j3 = j & 3;
        if (j3 != 0) {
            str = z ? " " : strUtc2Local;
        }
        if (j3 != 0) {
            TextViewBindingAdapter.setText(this.tv1, type);
            TextViewBindingAdapter.setText(this.tv3, strMbFormat);
            TextViewBindingAdapter.setText(this.tv4, str);
            this.tvExpired.setVisibility(i);
        }
    }
}
