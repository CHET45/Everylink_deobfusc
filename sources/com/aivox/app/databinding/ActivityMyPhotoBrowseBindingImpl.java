package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.ViewPagerFix;

/* JADX INFO: loaded from: classes.dex */
public class ActivityMyPhotoBrowseBindingImpl extends ActivityMyPhotoBrowseBinding {
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
        sparseIntArray.put(C0726R.id.viewPager, 1);
        sparseIntArray.put(C0726R.id.gl_function, 2);
        sparseIntArray.put(C0726R.id.ll_save, 3);
        sparseIntArray.put(C0726R.id.ll_favorite, 4);
        sparseIntArray.put(C0726R.id.iv_favorite, 5);
        sparseIntArray.put(C0726R.id.ll_delete, 6);
        sparseIntArray.put(C0726R.id.indicator, 7);
        sparseIntArray.put(C0726R.id.title_view, 8);
    }

    public ActivityMyPhotoBrowseBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 9, sIncludes, sViewsWithIds));
    }

    private ActivityMyPhotoBrowseBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[0], (GridLayout) objArr[2], (TextView) objArr[7], (ImageView) objArr[5], (LinearLayout) objArr[6], (LinearLayout) objArr[4], (LinearLayout) objArr[3], (HeadTitleLinearView) objArr[8], (ViewPagerFix) objArr[1]);
        this.mDirtyFlags = -1L;
        this.container.setTag(null);
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
