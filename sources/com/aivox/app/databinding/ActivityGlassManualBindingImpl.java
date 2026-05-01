package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.app.generated.callback.OnClickListener;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.HeadTitleLinearView;

/* JADX INFO: loaded from: classes.dex */
public class ActivityGlassManualBindingImpl extends ActivityGlassManualBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback62;
    private final View.OnClickListener mCallback63;
    private final View.OnClickListener mCallback64;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.title_view, 4);
        sparseIntArray.put(C0726R.id.ll_tab, 5);
        sparseIntArray.put(C0726R.id.cl_guide, 6);
        sparseIntArray.put(C0726R.id.iv_full, 7);
        sparseIntArray.put(C0726R.id.ll_left, 8);
        sparseIntArray.put(C0726R.id.ll_right, 9);
        sparseIntArray.put(C0726R.id.ll_wakeup, 10);
    }

    public ActivityGlassManualBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 11, sIncludes, sViewsWithIds));
    }

    private ActivityGlassManualBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[6], (ImageView) objArr[7], (LinearLayout) objArr[8], (LinearLayout) objArr[9], (LinearLayout) objArr[5], (LinearLayout) objArr[10], (HeadTitleLinearView) objArr[4], (TextView) objArr[1], (TextView) objArr[3], (TextView) objArr[2]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvGuide.setTag(null);
        this.tvManual.setTag(null);
        this.tvWakeup.setTag(null);
        setRootTag(view2);
        this.mCallback63 = new OnClickListener(this, 2);
        this.mCallback64 = new OnClickListener(this, 3);
        this.mCallback62 = new OnClickListener(this, 1);
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
        if (1 != i) {
            return false;
        }
        setClickListener((OnViewClickListener) obj);
        return true;
    }

    @Override // com.aivox.app.databinding.ActivityGlassManualBinding
    public void setClickListener(OnViewClickListener onViewClickListener) {
        this.mClickListener = onViewClickListener;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(1);
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
            this.tvGuide.setOnClickListener(this.mCallback62);
            this.tvManual.setOnClickListener(this.mCallback64);
            this.tvWakeup.setOnClickListener(this.mCallback63);
        }
    }

    @Override // com.aivox.app.generated.callback.OnClickListener.Listener
    public final void _internalCallbackOnClick(int i, View view2) {
        OnViewClickListener onViewClickListener;
        if (i == 1) {
            OnViewClickListener onViewClickListener2 = this.mClickListener;
            if (onViewClickListener2 != null) {
                onViewClickListener2.onViewClick(view2);
                return;
            }
            return;
        }
        if (i != 2) {
            if (i == 3 && (onViewClickListener = this.mClickListener) != null) {
                onViewClickListener.onViewClick(view2);
                return;
            }
            return;
        }
        OnViewClickListener onViewClickListener3 = this.mClickListener;
        if (onViewClickListener3 != null) {
            onViewClickListener3.onViewClick(view2);
        }
    }
}
