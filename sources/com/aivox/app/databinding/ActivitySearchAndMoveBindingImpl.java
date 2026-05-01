package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.common_ui.PowerfulEditText;

/* JADX INFO: loaded from: classes.dex */
public class ActivitySearchAndMoveBindingImpl extends ActivitySearchAndMoveBinding {
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
        sparseIntArray.put(C0726R.id.iv_close, 1);
        sparseIntArray.put(C0726R.id.tv_title, 2);
        sparseIntArray.put(C0726R.id.iv_apply, 3);
        sparseIntArray.put(C0726R.id.ll_search, 4);
        sparseIntArray.put(C0726R.id.et_search, 5);
        sparseIntArray.put(C0726R.id.rv_list, 6);
    }

    public ActivitySearchAndMoveBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 7, sIncludes, sViewsWithIds));
    }

    private ActivitySearchAndMoveBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (PowerfulEditText) objArr[5], (ImageView) objArr[3], (ImageView) objArr[1], (LinearLayout) objArr[4], (LinearLayout) objArr[0], (RecyclerView) objArr[6], (TextView) objArr[2]);
        this.mDirtyFlags = -1L;
        this.main.setTag(null);
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
