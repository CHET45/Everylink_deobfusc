package com.aivox.common_ui.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public class SettingTileLayoutBindingImpl extends SettingTileLayoutBinding {
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
        sparseIntArray.put(C1034R.id.cl_content, 1);
        sparseIntArray.put(C1034R.id.iv_title_leading, 2);
        sparseIntArray.put(C1034R.id.tv_title, 3);
        sparseIntArray.put(C1034R.id.iv_title_icon, 4);
        sparseIntArray.put(C1034R.id.iv_title_dot, 5);
        sparseIntArray.put(C1034R.id.tv_sub_title, 6);
        sparseIntArray.put(C1034R.id.tv_msg, 7);
        sparseIntArray.put(C1034R.id.iv_msg_dot, 8);
        sparseIntArray.put(C1034R.id.iv_right_icon, 9);
        sparseIntArray.put(C1034R.id.switch_right, 10);
        sparseIntArray.put(C1034R.id.divider, 11);
    }

    public SettingTileLayoutBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 12, sIncludes, sViewsWithIds));
    }

    private SettingTileLayoutBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[1], (ConstraintLayout) objArr[11], (ImageView) objArr[8], (ImageView) objArr[9], (ImageView) objArr[5], (ImageView) objArr[4], (ImageView) objArr[2], (LinearLayout) objArr[0], (SwitchCompat) objArr[10], (TextView) objArr[7], (TextView) objArr[6], (TextView) objArr[3]);
        this.mDirtyFlags = -1L;
        this.llContainer.setTag(null);
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
