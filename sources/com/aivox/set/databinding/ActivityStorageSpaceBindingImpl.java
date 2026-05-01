package com.aivox.set.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.set.C1105BR;
import com.aivox.set.C1106R;
import com.aivox.set.generated.callback.OnClickListener;

/* JADX INFO: loaded from: classes.dex */
public class ActivityStorageSpaceBindingImpl extends ActivityStorageSpaceBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback3;
    private final View.OnClickListener mCallback4;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C1106R.id.title_view, 3);
        sparseIntArray.put(C1106R.id.cl_storage_total, 4);
        sparseIntArray.put(C1106R.id.tv_storage, 5);
        sparseIntArray.put(C1106R.id.tv_storage_used_percent, 6);
        sparseIntArray.put(C1106R.id.tv_storage_size_cloud, 7);
        sparseIntArray.put(C1106R.id.cl_storage_audio, 8);
        sparseIntArray.put(C1106R.id.tv_storage_size_audio, 9);
        sparseIntArray.put(C1106R.id.tv_storage_audio_desc, 10);
        sparseIntArray.put(C1106R.id.tv_storage_cache, 11);
        sparseIntArray.put(C1106R.id.cl_temp_storage, 12);
        sparseIntArray.put(C1106R.id.tv_storage_size_cache, 13);
        sparseIntArray.put(C1106R.id.tv_storage_cache_desc, 14);
    }

    public ActivityStorageSpaceBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 15, sIncludes, sViewsWithIds));
    }

    private ActivityStorageSpaceBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[8], (ConstraintLayout) objArr[4], (ConstraintLayout) objArr[12], (HeadTitleLinearView) objArr[3], (TextView) objArr[1], (TextView) objArr[2], (TextView) objArr[5], (TextView) objArr[10], (TextView) objArr[11], (TextView) objArr[14], (TextView) objArr[9], (TextView) objArr[13], (TextView) objArr[7], (TextView) objArr[6]);
        this.mDirtyFlags = -1L;
        LinearLayout linearLayout = (LinearLayout) objArr[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        this.tvClearAudio.setTag(null);
        this.tvClearCache.setTag(null);
        setRootTag(view2);
        this.mCallback3 = new OnClickListener(this, 1);
        this.mCallback4 = new OnClickListener(this, 2);
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

    @Override // com.aivox.set.databinding.ActivityStorageSpaceBinding
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
            this.tvClearAudio.setOnClickListener(this.mCallback3);
            this.tvClearCache.setOnClickListener(this.mCallback4);
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
