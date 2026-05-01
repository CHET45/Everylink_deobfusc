package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.app.generated.callback.OnClickListener;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.DialogTitleItemView;
import com.aivox.common_ui.DialogTitleView;
import com.aivox.common_ui.LoadingButton;
import com.aivox.common_ui.PowerfulEditText;
import com.aivox.common_ui.RoundedCornerBitmap;

/* JADX INFO: loaded from: classes.dex */
public class AudioInAppShareViewBindingImpl extends AudioInAppShareViewBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback1;
    private final View.OnClickListener mCallback2;
    private final View.OnClickListener mCallback3;
    private final View.OnClickListener mCallback4;
    private final View.OnClickListener mCallback5;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.dtv_title, 6);
        sparseIntArray.put(C0726R.id.ll_search, 7);
        sparseIntArray.put(C0726R.id.et_search, 8);
        sparseIntArray.put(C0726R.id.dtiv_item_title, 9);
        sparseIntArray.put(C0726R.id.ll_user_info, 10);
        sparseIntArray.put(C0726R.id.tv_user_avatar, 11);
        sparseIntArray.put(C0726R.id.iv_user_avatar, 12);
        sparseIntArray.put(C0726R.id.tv_user_name, 13);
        sparseIntArray.put(C0726R.id.dtiv_time_title, 14);
        sparseIntArray.put(C0726R.id.hsv_valid_time, 15);
    }

    public AudioInAppShareViewBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 16, sIncludes, sViewsWithIds));
    }

    private AudioInAppShareViewBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (LoadingButton) objArr[5], (DialogTitleItemView) objArr[9], (DialogTitleItemView) objArr[14], (DialogTitleView) objArr[6], (PowerfulEditText) objArr[8], (HorizontalScrollView) objArr[15], (RoundedCornerBitmap) objArr[12], (LinearLayout) objArr[7], (LinearLayout) objArr[10], (TextView) objArr[1], (TextView) objArr[3], (TextView) objArr[2], (TextView) objArr[4], (TextView) objArr[11], (TextView) objArr[13]);
        this.mDirtyFlags = -1L;
        this.btnContinue.setTag(null);
        LinearLayout linearLayout = (LinearLayout) objArr[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        this.tvDay1.setTag(null);
        this.tvDay30.setTag(null);
        this.tvDay7.setTag(null);
        this.tvDay90.setTag(null);
        setRootTag(view2);
        this.mCallback5 = new OnClickListener(this, 5);
        this.mCallback3 = new OnClickListener(this, 3);
        this.mCallback4 = new OnClickListener(this, 4);
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
        if (1 != i) {
            return false;
        }
        setClickListener((OnViewClickListener) obj);
        return true;
    }

    @Override // com.aivox.app.databinding.AudioInAppShareViewBinding
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
            this.btnContinue.setOnClickListener(this.mCallback5);
            this.tvDay1.setOnClickListener(this.mCallback1);
            this.tvDay30.setOnClickListener(this.mCallback3);
            this.tvDay7.setOnClickListener(this.mCallback2);
            this.tvDay90.setOnClickListener(this.mCallback4);
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
        if (i == 2) {
            OnViewClickListener onViewClickListener3 = this.mClickListener;
            if (onViewClickListener3 != null) {
                onViewClickListener3.onViewClick(view2);
                return;
            }
            return;
        }
        if (i == 3) {
            OnViewClickListener onViewClickListener4 = this.mClickListener;
            if (onViewClickListener4 != null) {
                onViewClickListener4.onViewClick(view2);
                return;
            }
            return;
        }
        if (i != 4) {
            if (i == 5 && (onViewClickListener = this.mClickListener) != null) {
                onViewClickListener.onViewClick(view2);
                return;
            }
            return;
        }
        OnViewClickListener onViewClickListener5 = this.mClickListener;
        if (onViewClickListener5 != null) {
            onViewClickListener5.onViewClick(view2);
        }
    }
}
