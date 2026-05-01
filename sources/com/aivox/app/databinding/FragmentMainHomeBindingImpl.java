package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.app.generated.callback.OnClickListener;
import com.aivox.base.databinding.OnViewClickListener;

/* JADX INFO: loaded from: classes.dex */
public class FragmentMainHomeBindingImpl extends FragmentMainHomeBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback14;
    private final View.OnClickListener mCallback15;
    private final View.OnClickListener mCallback16;
    private final View.OnClickListener mCallback17;
    private final View.OnClickListener mCallback18;
    private final View.OnClickListener mCallback19;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.cl_home_top_bar, 7);
        sparseIntArray.put(C0726R.id.ll_noti, 8);
        sparseIntArray.put(C0726R.id.tv_noti, 9);
        sparseIntArray.put(C0726R.id.iv_splash_logo, 10);
        sparseIntArray.put(C0726R.id.iv_dot_sys, 11);
        sparseIntArray.put(C0726R.id.iv_dot_msg, 12);
        sparseIntArray.put(C0726R.id.tv_shorthand, 13);
        sparseIntArray.put(C0726R.id.tv_translate, 14);
        sparseIntArray.put(C0726R.id.tv_bilingual, 15);
        sparseIntArray.put(C0726R.id.tv_devices, 16);
        sparseIntArray.put(C0726R.id.tv_device_connected, 17);
        sparseIntArray.put(C0726R.id.tv_recent_files, 18);
        sparseIntArray.put(C0726R.id.tv_no_files, 19);
        sparseIntArray.put(C0726R.id.rv_recent, 20);
    }

    public FragmentMainHomeBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 21, sIncludes, sViewsWithIds));
    }

    private FragmentMainHomeBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[7], (ImageView) objArr[12], (ImageView) objArr[11], (ImageView) objArr[6], (ImageView) objArr[1], (ImageView) objArr[10], (LinearLayout) objArr[4], (LinearLayout) objArr[5], (LinearLayout) objArr[8], (LinearLayout) objArr[2], (LinearLayout) objArr[3], (RecyclerView) objArr[20], (TextView) objArr[15], (TextView) objArr[17], (TextView) objArr[16], (TextView) objArr[19], (TextView) objArr[9], (TextView) objArr[18], (TextView) objArr[13], (TextView) objArr[14]);
        this.mDirtyFlags = -1L;
        this.ivImport.setTag(null);
        this.ivNotice.setTag(null);
        this.llBilingual.setTag(null);
        this.llDevices.setTag(null);
        this.llTranscribe.setTag(null);
        this.llTranslate.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setRootTag(view2);
        this.mCallback17 = new OnClickListener(this, 4);
        this.mCallback15 = new OnClickListener(this, 2);
        this.mCallback19 = new OnClickListener(this, 6);
        this.mCallback16 = new OnClickListener(this, 3);
        this.mCallback14 = new OnClickListener(this, 1);
        this.mCallback18 = new OnClickListener(this, 5);
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

    @Override // com.aivox.app.databinding.FragmentMainHomeBinding
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
            this.ivImport.setOnClickListener(this.mCallback19);
            this.ivNotice.setOnClickListener(this.mCallback14);
            this.llBilingual.setOnClickListener(this.mCallback17);
            this.llDevices.setOnClickListener(this.mCallback18);
            this.llTranscribe.setOnClickListener(this.mCallback15);
            this.llTranslate.setOnClickListener(this.mCallback16);
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
        }
    }
}
