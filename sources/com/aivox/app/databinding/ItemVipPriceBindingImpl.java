package com.aivox.app.databinding;

import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.databinding.adapters.ViewBindingAdapter;
import com.aivox.account.C0707R;
import com.aivox.base.C0874R;
import com.aivox.common.model.VipSelect;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public class ItemVipPriceBindingImpl extends ItemVipPriceBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    public ItemVipPriceBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 1, sIncludes, sViewsWithIds));
    }

    private ItemVipPriceBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (TextView) objArr[0]);
        this.mDirtyFlags = -1L;
        this.tvCommon1.setTag(null);
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

    @Override // com.aivox.app.databinding.ItemVipPriceBinding
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
        String str;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        VipSelect vipSelect = this.mXmlmodel;
        long j2 = j & 3;
        Drawable drawable = null;
        String price = null;
        int i = 0;
        boolean zIsSelect = false;
        if (j2 != 0) {
            if (vipSelect != null) {
                zIsSelect = vipSelect.isSelect();
                price = vipSelect.getPrice();
            }
            if (j2 != 0) {
                j |= zIsSelect ? 40L : 20L;
            }
            int colorFromResource = getColorFromResource(this.tvCommon1, zIsSelect ? C0707R.color.white : C0874R.color.vip_bg_stroke);
            Drawable drawable2 = AppCompatResources.getDrawable(this.tvCommon1.getContext(), zIsSelect ? C1034R.drawable.vip_select : C1034R.drawable.vip_unselect);
            i = colorFromResource;
            str = price;
            drawable = drawable2;
        } else {
            str = null;
        }
        if ((j & 3) != 0) {
            ViewBindingAdapter.setBackground(this.tvCommon1, drawable);
            TextViewBindingAdapter.setText(this.tvCommon1, str);
            this.tvCommon1.setTextColor(i);
        }
    }
}
