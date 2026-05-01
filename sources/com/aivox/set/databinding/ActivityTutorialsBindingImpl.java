package com.aivox.set.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.SettingTileView;
import com.aivox.set.C1106R;

/* JADX INFO: loaded from: classes.dex */
public class ActivityTutorialsBindingImpl extends ActivityTutorialsBinding {
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
        sparseIntArray.put(C1106R.id.title_view, 1);
        sparseIntArray.put(C1106R.id.stv_main, 2);
        sparseIntArray.put(C1106R.id.stv_files, 3);
        sparseIntArray.put(C1106R.id.stv_bilingual, 4);
        sparseIntArray.put(C1106R.id.vp_guide, 5);
        sparseIntArray.put(C1106R.id.view_skip, 6);
        sparseIntArray.put(C1106R.id.view_prev, 7);
        sparseIntArray.put(C1106R.id.view_next, 8);
        sparseIntArray.put(C1106R.id.gp_guide, 9);
    }

    public ActivityTutorialsBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 10, sIncludes, sViewsWithIds));
    }

    private ActivityTutorialsBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (Group) objArr[9], (ConstraintLayout) objArr[0], (SettingTileView) objArr[4], (SettingTileView) objArr[3], (SettingTileView) objArr[2], (HeadTitleLinearView) objArr[1], (View) objArr[8], (View) objArr[7], (View) objArr[6], (ViewPager) objArr[5]);
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
