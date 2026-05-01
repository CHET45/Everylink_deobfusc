package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.PowerfulEditText;

/* JADX INFO: loaded from: classes.dex */
public class ActivityRecordImportBindingImpl extends ActivityRecordImportBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

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
        sparseIntArray.put(C0726R.id.cl_top, 1);
        sparseIntArray.put(C0726R.id.title_view, 2);
        sparseIntArray.put(C0726R.id.ll_search_layout, 3);
        sparseIntArray.put(C0726R.id.et_search_import, 4);
        sparseIntArray.put(C0726R.id.iv_search, 5);
        sparseIntArray.put(C0726R.id.tv_search_cancel, 6);
        sparseIntArray.put(C0726R.id.barrier_cancel, 7);
        sparseIntArray.put(C0726R.id.refreshLayout, 8);
        sparseIntArray.put(C0726R.id.recyclerview, 9);
        sparseIntArray.put(C0726R.id.ll_empty, 10);
        sparseIntArray.put(C0726R.id.f118tv, 11);
        sparseIntArray.put(C0726R.id.sv_import_notice, 12);
        sparseIntArray.put(C0726R.id.tv_do_import, 13);
    }

    public ActivityRecordImportBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 14, sIncludes, sViewsWithIds));
    }

    private ActivityRecordImportBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (Barrier) objArr[7], (ConstraintLayout) objArr[1], (PowerfulEditText) objArr[4], (ImageView) objArr[5], (LinearLayout) objArr[10], (LinearLayout) objArr[3], (RecyclerView) objArr[9], (RelativeLayout) objArr[8], (ScrollView) objArr[12], (HeadTitleLinearView) objArr[2], (TextView) objArr[11], (TextView) objArr[13], (TextView) objArr[6]);
        this.mDirtyFlags = -1L;
        LinearLayout linearLayout = (LinearLayout) objArr[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
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
