package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;

/* JADX INFO: loaded from: classes.dex */
public class ItemHomeAiChatBindingImpl extends ItemHomeAiChatBinding {
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
        sparseIntArray.put(C0726R.id.tv_question, 1);
        sparseIntArray.put(C0726R.id.iv_image, 2);
        sparseIntArray.put(C0726R.id.cl_answer, 3);
        sparseIntArray.put(C0726R.id.iv_bot_avatar, 4);
        sparseIntArray.put(C0726R.id.tv_name, 5);
        sparseIntArray.put(C0726R.id.tv_answer, 6);
        sparseIntArray.put(C0726R.id.iv_regenerate, 7);
        sparseIntArray.put(C0726R.id.iv_copy, 8);
        sparseIntArray.put(C0726R.id.iv_delete, 9);
        sparseIntArray.put(C0726R.id.v_line, 10);
    }

    public ItemHomeAiChatBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 11, sIncludes, sViewsWithIds));
    }

    private ItemHomeAiChatBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[3], (ImageView) objArr[4], (ImageView) objArr[8], (ImageView) objArr[9], (ImageView) objArr[2], (ImageView) objArr[7], (TextView) objArr[6], (TextView) objArr[5], (TextView) objArr[1], (View) objArr[10]);
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
