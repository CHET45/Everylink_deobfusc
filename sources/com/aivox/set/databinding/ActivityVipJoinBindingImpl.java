package com.aivox.set.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.VipPackView;
import com.aivox.set.C1106R;

/* JADX INFO: loaded from: classes.dex */
public class ActivityVipJoinBindingImpl extends ActivityVipJoinBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, Object obj) {
        return true;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C1106R.id.ll_top_btn, 1);
        sparseIntArray.put(C1106R.id.iv_close, 2);
        sparseIntArray.put(C1106R.id.cl_logo, 3);
        sparseIntArray.put(C1106R.id.iv_logo, 4);
        sparseIntArray.put(C1106R.id.iv_title, 5);
        sparseIntArray.put(C1106R.id.rl_content, 6);
        sparseIntArray.put(C1106R.id.ll_vip_switch, 7);
        sparseIntArray.put(C1106R.id.vpv_month, 8);
        sparseIntArray.put(C1106R.id.tv_agreement, 9);
        sparseIntArray.put(C1106R.id.vpv_year, 10);
        sparseIntArray.put(C1106R.id.cv_trial, 11);
        sparseIntArray.put(C1106R.id.view_cover, 12);
    }

    public ActivityVipJoinBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 13, sIncludes, sViewsWithIds));
    }

    private ActivityVipJoinBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[3], (ConstraintLayout) objArr[0], (CardView) objArr[11], (ImageView) objArr[2], (ImageView) objArr[4], (ImageView) objArr[5], (LinearLayout) objArr[1], (LinearLayout) objArr[7], (RelativeLayout) objArr[6], (TextView) objArr[9], (View) objArr[12], (VipPackView) objArr[8], (VipPackView) objArr[10]);
        this.mDirtyFlags = -1L;
        this.clParent.setTag(null);
        setRootTag(view2);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1L;
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
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}
