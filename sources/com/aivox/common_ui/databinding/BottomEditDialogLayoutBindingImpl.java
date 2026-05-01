package com.aivox.common_ui.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.DialogTitleItemView;
import com.aivox.common_ui.DialogTitleView;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public class BottomEditDialogLayoutBindingImpl extends BottomEditDialogLayoutBinding {
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
        sparseIntArray.put(C1034R.id.iv_top, 1);
        sparseIntArray.put(C1034R.id.cl_audio_save, 2);
        sparseIntArray.put(C1034R.id.dtv_title, 3);
        sparseIntArray.put(C1034R.id.dtiv_item_title, 4);
        sparseIntArray.put(C1034R.id.et_content, 5);
        sparseIntArray.put(C1034R.id.iv_content_clear, 6);
        sparseIntArray.put(C1034R.id.dtiv_item_type, 7);
        sparseIntArray.put(C1034R.id.hsv_type_item, 8);
        sparseIntArray.put(C1034R.id.ll_type_item, 9);
        sparseIntArray.put(C1034R.id.btn_left, 10);
        sparseIntArray.put(C1034R.id.btn_save, 11);
    }

    public BottomEditDialogLayoutBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 12, sIncludes, sViewsWithIds));
    }

    private BottomEditDialogLayoutBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (LoadingButton) objArr[10], (LoadingButton) objArr[11], (ConstraintLayout) objArr[2], (DialogTitleItemView) objArr[4], (DialogTitleItemView) objArr[7], (DialogTitleView) objArr[3], (EditText) objArr[5], (HorizontalScrollView) objArr[8], (ImageView) objArr[6], (ImageView) objArr[1], (LinearLayout) objArr[9]);
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
