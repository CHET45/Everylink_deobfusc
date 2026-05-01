package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.exifinterface.media.ExifInterface;
import com.aivox.app.C0726R;
import com.aivox.base.C0874R;
import com.aivox.common.model.Identity;
import com.aivox.common_ui.RoundedCornerBitmap;

/* JADX INFO: loaded from: classes.dex */
public class ItemAccoutBindingImpl extends ItemAccoutBinding {
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
        sparseIntArray.put(C0726R.id.iv_head, 3);
    }

    public ItemAccoutBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 4, sIncludes, sViewsWithIds));
    }

    private ItemAccoutBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ImageView) objArr[2], (RoundedCornerBitmap) objArr[3], (TextView) objArr[1]);
        this.mDirtyFlags = -1L;
        this.f123iv.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.f124tv.setTag(null);
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
        setXmlmodel((Identity) obj);
        return true;
    }

    @Override // com.aivox.app.databinding.ItemAccoutBinding
    public void setXmlmodel(Identity identity) {
        this.mXmlmodel = identity;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(6);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        boolean zEquals;
        int colorFromResource;
        int i;
        String identity;
        String str;
        boolean zIsSelect;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        Identity identity2 = this.mXmlmodel;
        long j2 = j & 3;
        if (j2 != 0) {
            if (identity2 != null) {
                identity = identity2.getIdentity();
                zIsSelect = identity2.isSelect();
            } else {
                identity = null;
                zIsSelect = false;
            }
            if (j2 != 0) {
                j |= zIsSelect ? 544L : 272L;
            }
            zEquals = identity != null ? identity.equals("1") : false;
            if ((j & 3) != 0) {
                j = zEquals ? j | 128 : j | 64;
            }
            int i2 = zIsSelect ? 0 : 8;
            colorFromResource = getColorFromResource(this.f124tv, zIsSelect ? C0874R.color.home_txt : C0874R.color.txt_black);
            i = i2;
        } else {
            zEquals = false;
            colorFromResource = 0;
            i = 0;
            identity = null;
        }
        String usernickname = ((128 & j) == 0 || identity2 == null) ? null : identity2.getUsernickname();
        long j3 = 64 & j;
        if (j3 != 0) {
            boolean zEquals2 = identity != null ? identity.equals(ExifInterface.GPS_MEASUREMENT_2D) : false;
            if (j3 != 0) {
                j |= zEquals2 ? 8L : 4L;
            }
            str = (identity2 != null ? identity2.getCompany() : null) + this.f124tv.getResources().getString(zEquals2 ? C0874R.string.home_company_admin : C0874R.string.home_company_child);
        } else {
            str = null;
        }
        long j4 = j & 3;
        if (j4 == 0) {
            str = null;
        } else if (zEquals) {
            str = usernickname;
        }
        if (j4 != 0) {
            this.f123iv.setVisibility(i);
            TextViewBindingAdapter.setText(this.f124tv, str);
            this.f124tv.setTextColor(colorFromResource);
        }
    }
}
