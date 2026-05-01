package com.aivox.set.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.SettingTileView;
import com.aivox.set.C1105BR;
import com.aivox.set.C1106R;
import com.aivox.set.generated.callback.OnClickListener;

/* JADX INFO: loaded from: classes.dex */
public class ActivityMembershipBindingImpl extends ActivityMembershipBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback1;
    private final View.OnClickListener mCallback2;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C1106R.id.title_view, 3);
    }

    public ActivityMembershipBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 4, sIncludes, sViewsWithIds));
    }

    private ActivityMembershipBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (SettingTileView) objArr[1], (SettingTileView) objArr[2], (HeadTitleLinearView) objArr[3]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.stvHistory.setTag(null);
        this.stvUpgrade.setTag(null);
        setRootTag(view2);
        this.mCallback1 = new OnClickListener(this, 1);
        this.mCallback2 = new OnClickListener(this, 2);
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
        if (C1105BR.clickListener != i) {
            return false;
        }
        setClickListener((OnViewClickListener) obj);
        return true;
    }

    @Override // com.aivox.set.databinding.ActivityMembershipBinding
    public void setClickListener(OnViewClickListener onViewClickListener) {
        this.mClickListener = onViewClickListener;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(C1105BR.clickListener);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        OnViewClickListener onViewClickListener = this.mClickListener;
        if ((j & 2) != 0) {
            this.stvHistory.setOnClickListener(this.mCallback1);
            this.stvUpgrade.setOnClickListener(this.mCallback2);
        }
    }

    @Override // com.aivox.set.generated.callback.OnClickListener.Listener
    public final void _internalCallbackOnClick(int i, View view2) {
        OnViewClickListener onViewClickListener;
        if (i != 1) {
            if (i == 2 && (onViewClickListener = this.mClickListener) != null) {
                onViewClickListener.onViewClick(view2);
                return;
            }
            return;
        }
        OnViewClickListener onViewClickListener2 = this.mClickListener;
        if (onViewClickListener2 != null) {
            onViewClickListener2.onViewClick(view2);
        }
    }
}
