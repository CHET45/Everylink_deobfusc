package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.app.generated.callback.OnClickListener;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public class FragmentMainFileBindingImpl extends FragmentMainFileBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback54;
    private final View.OnClickListener mCallback55;
    private final View.OnClickListener mCallback56;
    private final View.OnClickListener mCallback57;
    private final View.OnClickListener mCallback58;
    private final View.OnClickListener mCallback59;
    private final View.OnClickListener mCallback60;
    private final View.OnClickListener mCallback61;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.rl_toolbar, 9);
        sparseIntArray.put(C0726R.id.ll_toolbar, 10);
        sparseIntArray.put(C0726R.id.tv_folder_cur, 11);
        sparseIntArray.put(C0726R.id.rv_tabs, 12);
        sparseIntArray.put(C0726R.id.fl_container, 13);
    }

    public FragmentMainFileBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 14, sIncludes, sViewsWithIds));
    }

    private FragmentMainFileBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (LoadingButton) objArr[8], (FrameLayout) objArr[13], (ImageView) objArr[2], (ImageView) objArr[3], (ImageView) objArr[4], (ImageView) objArr[1], (ImageView) objArr[5], (ImageView) objArr[7], (LinearLayout) objArr[6], (LinearLayout) objArr[10], (RelativeLayout) objArr[9], (RecyclerView) objArr[12], (TextView) objArr[11]);
        this.mDirtyFlags = -1L;
        this.btnDeleteAll.setTag(null);
        this.ivCreateFolder.setTag(null);
        this.ivDeleteFolder.setTag(null);
        this.ivEditFolder.setTag(null);
        this.ivImportFile.setTag(null);
        this.ivMoveFolder.setTag(null);
        this.ivSort.setTag(null);
        this.llSearch.setTag(null);
        LinearLayout linearLayout = (LinearLayout) objArr[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        setRootTag(view2);
        this.mCallback58 = new OnClickListener(this, 5);
        this.mCallback59 = new OnClickListener(this, 6);
        this.mCallback56 = new OnClickListener(this, 3);
        this.mCallback57 = new OnClickListener(this, 4);
        this.mCallback54 = new OnClickListener(this, 1);
        this.mCallback55 = new OnClickListener(this, 2);
        this.mCallback61 = new OnClickListener(this, 8);
        this.mCallback60 = new OnClickListener(this, 7);
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

    @Override // com.aivox.app.databinding.FragmentMainFileBinding
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
            this.btnDeleteAll.setOnClickListener(this.mCallback61);
            this.ivCreateFolder.setOnClickListener(this.mCallback55);
            this.ivDeleteFolder.setOnClickListener(this.mCallback56);
            this.ivEditFolder.setOnClickListener(this.mCallback57);
            this.ivImportFile.setOnClickListener(this.mCallback54);
            this.ivMoveFolder.setOnClickListener(this.mCallback58);
            this.ivSort.setOnClickListener(this.mCallback60);
            this.llSearch.setOnClickListener(this.mCallback59);
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
        }
    }
}
