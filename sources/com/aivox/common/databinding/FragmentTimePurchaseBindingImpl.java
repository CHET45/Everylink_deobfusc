package com.aivox.common.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.common.C0958R;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public class FragmentTimePurchaseBindingImpl extends FragmentTimePurchaseBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

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
        sparseIntArray.put(C0958R.id.tv_title, 1);
        sparseIntArray.put(C0958R.id.iv_close, 2);
        sparseIntArray.put(C0958R.id.tv_msg, 3);
        sparseIntArray.put(C0958R.id.ll_time_pack, 4);
        sparseIntArray.put(C0958R.id.tv_time_pack, 5);
        sparseIntArray.put(C0958R.id.tv_valid_until, 6);
        sparseIntArray.put(C0958R.id.tv_agreement, 7);
        sparseIntArray.put(C0958R.id.btn_pay_now, 8);
        sparseIntArray.put(C0958R.id.ll_cancel_sub, 9);
        sparseIntArray.put(C0958R.id.btn_do_cancel, 10);
        sparseIntArray.put(C0958R.id.btn_do_not_cancel, 11);
    }

    public FragmentTimePurchaseBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 12, sIncludes, sViewsWithIds));
    }

    private FragmentTimePurchaseBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (LoadingButton) objArr[10], (LoadingButton) objArr[11], (LoadingButton) objArr[8], (ImageView) objArr[2], (LinearLayout) objArr[9], (LinearLayout) objArr[4], (TextView) objArr[7], (TextView) objArr[3], (TextView) objArr[5], (TextView) objArr[1], (TextView) objArr[6]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
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
