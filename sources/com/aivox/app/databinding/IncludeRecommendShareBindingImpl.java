package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.app.generated.callback.OnClickListener;
import com.aivox.base.databinding.OnViewClickListener;

/* JADX INFO: loaded from: classes.dex */
public class IncludeRecommendShareBindingImpl extends IncludeRecommendShareBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback94;
    private final View.OnClickListener mCallback95;
    private final View.OnClickListener mCallback96;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.tv_share_title, 4);
        sparseIntArray.put(C0726R.id.line_share, 5);
        sparseIntArray.put(C0726R.id.cl_1_share, 6);
        sparseIntArray.put(C0726R.id.iv_wechat, 7);
        sparseIntArray.put(C0726R.id.file_share_wechat, 8);
        sparseIntArray.put(C0726R.id.iv_qq, 9);
        sparseIntArray.put(C0726R.id.file_share_qq, 10);
        sparseIntArray.put(C0726R.id.cl_circle, 11);
        sparseIntArray.put(C0726R.id.iv_circle, 12);
        sparseIntArray.put(C0726R.id.file_share_circle, 13);
        sparseIntArray.put(C0726R.id.iv_share_url, 14);
        sparseIntArray.put(C0726R.id.tv_share_url, 15);
        sparseIntArray.put(C0726R.id.btn_close, 16);
        sparseIntArray.put(C0726R.id.tv_toshare, 17);
    }

    public IncludeRecommendShareBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 18, sIncludes, sViewsWithIds));
    }

    private IncludeRecommendShareBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (TextView) objArr[16], (ConstraintLayout) objArr[6], (ConstraintLayout) objArr[11], (ConstraintLayout) objArr[2], (ConstraintLayout) objArr[3], (ConstraintLayout) objArr[1], (TextView) objArr[13], (TextView) objArr[10], (TextView) objArr[8], (ImageView) objArr[12], (ImageView) objArr[9], (ImageView) objArr[14], (ImageView) objArr[7], (View) objArr[5], (TextView) objArr[4], (TextView) objArr[15], (TextView) objArr[17]);
        this.mDirtyFlags = -1L;
        this.clQq.setTag(null);
        this.clShareUrl.setTag(null);
        this.clWechat.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setRootTag(view2);
        this.mCallback96 = new OnClickListener(this, 3);
        this.mCallback94 = new OnClickListener(this, 1);
        this.mCallback95 = new OnClickListener(this, 2);
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

    @Override // com.aivox.app.databinding.IncludeRecommendShareBinding
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
            this.clQq.setOnClickListener(this.mCallback95);
            this.clShareUrl.setOnClickListener(this.mCallback96);
            this.clWechat.setOnClickListener(this.mCallback94);
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
