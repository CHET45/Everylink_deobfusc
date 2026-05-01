package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.app.generated.callback.OnClickListener;
import com.aivox.base.databinding.OnViewClickListener;
import com.google.android.material.progressindicator.CircularProgressIndicator;

/* JADX INFO: loaded from: classes.dex */
public class FragmentGallaryBindingImpl extends FragmentGallaryBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback100;
    private final View.OnClickListener mCallback101;
    private final View.OnClickListener mCallback102;
    private final View.OnClickListener mCallback103;
    private final View.OnClickListener mCallback104;
    private final View.OnClickListener mCallback105;
    private final View.OnClickListener mCallback106;
    private final View.OnClickListener mCallback107;
    private final View.OnClickListener mCallback108;
    private final View.OnClickListener mCallback97;
    private final View.OnClickListener mCallback98;
    private final View.OnClickListener mCallback99;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.tv_title, 13);
        sparseIntArray.put(C0726R.id.ll_tab, 14);
        sparseIntArray.put(C0726R.id.tv_select_count, 15);
        sparseIntArray.put(C0726R.id.rl_sync, 16);
        sparseIntArray.put(C0726R.id.ll_sync, 17);
        sparseIntArray.put(C0726R.id.tv_file_count, 18);
        sparseIntArray.put(C0726R.id.cl_sync_progress, 19);
        sparseIntArray.put(C0726R.id.cpi, 20);
        sparseIntArray.put(C0726R.id.tv_progress, 21);
        sparseIntArray.put(C0726R.id.tv_file, 22);
        sparseIntArray.put(C0726R.id.tv_hint, 23);
        sparseIntArray.put(C0726R.id.rv_gallery, 24);
        sparseIntArray.put(C0726R.id.gl_function, 25);
        sparseIntArray.put(C0726R.id.iv_favorite, 26);
    }

    public FragmentGallaryBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 27, sIncludes, sViewsWithIds));
    }

    private FragmentGallaryBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[19], (CircularProgressIndicator) objArr[20], (GridLayout) objArr[25], (ImageView) objArr[9], (ImageView) objArr[26], (ImageView) objArr[1], (LinearLayout) objArr[12], (LinearLayout) objArr[11], (LinearLayout) objArr[10], (LinearLayout) objArr[17], (LinearLayout) objArr[14], (RelativeLayout) objArr[16], (RecyclerView) objArr[24], (TextView) objArr[2], (TextView) objArr[7], (TextView) objArr[5], (TextView) objArr[22], (TextView) objArr[18], (TextView) objArr[23], (TextView) objArr[3], (TextView) objArr[21], (TextView) objArr[6], (TextView) objArr[15], (TextView) objArr[8], (TextView) objArr[13], (TextView) objArr[4]);
        this.mDirtyFlags = -1L;
        this.ivCancel.setTag(null);
        this.ivSettings.setTag(null);
        this.llDelete.setTag(null);
        this.llFavorite.setTag(null);
        this.llSave.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvAll.setTag(null);
        this.tvComplete.setTag(null);
        this.tvFavorite.setTag(null);
        this.tvPhoto.setTag(null);
        this.tvSelectAll.setTag(null);
        this.tvSync.setTag(null);
        this.tvVideo.setTag(null);
        setRootTag(view2);
        this.mCallback106 = new OnClickListener(this, 10);
        this.mCallback104 = new OnClickListener(this, 8);
        this.mCallback102 = new OnClickListener(this, 6);
        this.mCallback100 = new OnClickListener(this, 4);
        this.mCallback99 = new OnClickListener(this, 3);
        this.mCallback108 = new OnClickListener(this, 12);
        this.mCallback107 = new OnClickListener(this, 11);
        this.mCallback105 = new OnClickListener(this, 9);
        this.mCallback103 = new OnClickListener(this, 7);
        this.mCallback98 = new OnClickListener(this, 2);
        this.mCallback101 = new OnClickListener(this, 5);
        this.mCallback97 = new OnClickListener(this, 1);
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

    @Override // com.aivox.app.databinding.FragmentGallaryBinding
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
            this.ivCancel.setOnClickListener(this.mCallback105);
            this.ivSettings.setOnClickListener(this.mCallback97);
            this.llDelete.setOnClickListener(this.mCallback108);
            this.llFavorite.setOnClickListener(this.mCallback107);
            this.llSave.setOnClickListener(this.mCallback106);
            this.tvAll.setOnClickListener(this.mCallback98);
            this.tvComplete.setOnClickListener(this.mCallback103);
            this.tvFavorite.setOnClickListener(this.mCallback101);
            this.tvPhoto.setOnClickListener(this.mCallback99);
            this.tvSelectAll.setOnClickListener(this.mCallback102);
            this.tvSync.setOnClickListener(this.mCallback104);
            this.tvVideo.setOnClickListener(this.mCallback100);
        }
    }

    @Override // com.aivox.app.generated.callback.OnClickListener.Listener
    public final void _internalCallbackOnClick(int i, View view2) {
        switch (i) {
            case 1:
                OnViewClickListener onViewClickListener = this.mClickListener;
                if (onViewClickListener != null) {
                    onViewClickListener.onViewClick(view2);
                }
                break;
            case 2:
                OnViewClickListener onViewClickListener2 = this.mClickListener;
                if (onViewClickListener2 != null) {
                    onViewClickListener2.onViewClick(view2);
                }
                break;
            case 3:
                OnViewClickListener onViewClickListener3 = this.mClickListener;
                if (onViewClickListener3 != null) {
                    onViewClickListener3.onViewClick(view2);
                }
                break;
            case 4:
                OnViewClickListener onViewClickListener4 = this.mClickListener;
                if (onViewClickListener4 != null) {
                    onViewClickListener4.onViewClick(view2);
                }
                break;
            case 5:
                OnViewClickListener onViewClickListener5 = this.mClickListener;
                if (onViewClickListener5 != null) {
                    onViewClickListener5.onViewClick(view2);
                }
                break;
            case 6:
                OnViewClickListener onViewClickListener6 = this.mClickListener;
                if (onViewClickListener6 != null) {
                    onViewClickListener6.onViewClick(view2);
                }
                break;
            case 7:
                OnViewClickListener onViewClickListener7 = this.mClickListener;
                if (onViewClickListener7 != null) {
                    onViewClickListener7.onViewClick(view2);
                }
                break;
            case 8:
                OnViewClickListener onViewClickListener8 = this.mClickListener;
                if (onViewClickListener8 != null) {
                    onViewClickListener8.onViewClick(view2);
                }
                break;
            case 9:
                OnViewClickListener onViewClickListener9 = this.mClickListener;
                if (onViewClickListener9 != null) {
                    onViewClickListener9.onViewClick(view2);
                }
                break;
            case 10:
                OnViewClickListener onViewClickListener10 = this.mClickListener;
                if (onViewClickListener10 != null) {
                    onViewClickListener10.onViewClick(view2);
                }
                break;
            case 11:
                OnViewClickListener onViewClickListener11 = this.mClickListener;
                if (onViewClickListener11 != null) {
                    onViewClickListener11.onViewClick(view2);
                }
                break;
            case 12:
                OnViewClickListener onViewClickListener12 = this.mClickListener;
                if (onViewClickListener12 != null) {
                    onViewClickListener12.onViewClick(view2);
                }
                break;
        }
    }
}
