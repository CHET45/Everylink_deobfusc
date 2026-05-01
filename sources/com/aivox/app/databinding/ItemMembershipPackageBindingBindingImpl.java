package com.aivox.app.databinding;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.ImageViewBindingAdapter;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.databinding.adapters.ViewBindingAdapter;
import com.aivox.common.model.VipSelect;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public class ItemMembershipPackageBindingBindingImpl extends ItemMembershipPackageBindingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final ConstraintLayout mboundView1;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    public ItemMembershipPackageBindingBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 5, sIncludes, sViewsWithIds));
    }

    private ItemMembershipPackageBindingBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ImageView) objArr[4], (TextView) objArr[2], (TextView) objArr[3]);
        this.mDirtyFlags = -1L;
        this.f130iv.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        ConstraintLayout constraintLayout2 = (ConstraintLayout) objArr[1];
        this.mboundView1 = constraintLayout2;
        constraintLayout2.setTag(null);
        this.tvContent.setTag(null);
        this.tvPrice.setTag(null);
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
        if (6 != i) {
            return false;
        }
        setXmlmodel((VipSelect) obj);
        return true;
    }

    @Override // com.aivox.app.databinding.ItemMembershipPackageBindingBinding
    public void setXmlmodel(VipSelect vipSelect) {
        this.mXmlmodel = vipSelect;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(6);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String price;
        Drawable drawable;
        boolean zIsSelect;
        Context context;
        int i;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        VipSelect vipSelect = this.mXmlmodel;
        long j2 = j & 3;
        Drawable drawable2 = null;
        if (j2 != 0) {
            if (vipSelect != null) {
                zIsSelect = vipSelect.isSelect();
                price = vipSelect.getPrice();
            } else {
                zIsSelect = false;
                price = null;
            }
            if (j2 != 0) {
                j |= zIsSelect ? 40L : 20L;
            }
            drawable = AppCompatResources.getDrawable(this.mboundView1.getContext(), zIsSelect ? C1034R.drawable.membership_package_item_bg : C1034R.drawable.membership_package_item_bg_);
            if (zIsSelect) {
                context = this.f130iv.getContext();
                i = C1034R.drawable.membership_package_tuijian;
            } else {
                context = this.f130iv.getContext();
                i = C1034R.drawable.membership_package_tehui;
            }
            drawable2 = AppCompatResources.getDrawable(context, i);
        } else {
            price = null;
            drawable = null;
        }
        if ((3 & j) != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.f130iv, drawable2);
            ViewBindingAdapter.setBackground(this.mboundView1, drawable);
            TextViewBindingAdapter.setText(this.tvPrice, price);
        }
        if ((j & 2) != 0) {
            TextViewBindingAdapter.setText(this.tvContent, "包月");
        }
    }
}
