package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.app.view.RippleAnimationView;

/* JADX INFO: loaded from: classes.dex */
public class ViewConversationRecordBindingImpl extends ViewConversationRecordBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.iv_pause, 2);
        sparseIntArray.put(C0726R.id.iv_mic, 3);
        sparseIntArray.put(C0726R.id.tv_time, 4);
    }

    public ViewConversationRecordBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 5, sIncludes, sViewsWithIds));
    }

    private ViewConversationRecordBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[0], (ImageView) objArr[3], (ImageView) objArr[2], (RippleAnimationView) objArr[1], (TextView) objArr[4]);
        this.mDirtyFlags = -1L;
        this.clParent.setTag(null);
        this.ripple.setTag(null);
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
        if (4 != i) {
            return false;
        }
        setRippersColor((Integer) obj);
        return true;
    }

    @Override // com.aivox.app.databinding.ViewConversationRecordBinding
    public void setRippersColor(Integer num) {
        this.mRippersColor = num;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        long j2 = j & 3;
        int iSafeUnbox = j2 != 0 ? ViewDataBinding.safeUnbox(this.mRippersColor) : 0;
        if (j2 != 0) {
            RippleAnimationView.ripple_anim_color(this.ripple, iSafeUnbox);
        }
    }
}
